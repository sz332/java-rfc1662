package com.acme.rfc1662.impl;

import static com.acme.rfc1662.IParsingStateMachine.State.END_OF_STREAM_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_ADDRESS_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_CONTROL_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_PROTOCOL_ONE_OCTET_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_PROTOCOL_TWO_OCTET_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.PARSE_VALID_MESSAGE_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_END_FLAG_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.SEPARATE_INFORMATION_FROM_CHECKSUM_STATE;

import java.io.ByteArrayInputStream;
import java.util.EnumMap;

import com.acme.rfc1662.EndOfStreamException;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.ParserResult;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.states.EndOfStreamState;
import com.acme.rfc1662.states.MatchAddressFieldState;
import com.acme.rfc1662.states.MatchControlFieldState;
import com.acme.rfc1662.states.MatchProtocolOneOctetFieldState;
import com.acme.rfc1662.states.MatchProtocolTwoOctetFieldState;
import com.acme.rfc1662.states.ParseValidMessageState;
import com.acme.rfc1662.states.ReadUntilEndingFlagState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;
import com.acme.rfc1662.states.SeparateInformationFromChecksumState;

public class ParsingStateMachine implements IParsingStateMachine {

	IParsingContext context;

	EnumMap<State, IParsingState> states = new EnumMap<>(State.class);

	Protocol protocol;
	FrameCheckSequence fcs;
	
	public ParsingStateMachine(Protocol protocol, FrameCheckSequence fcs) {
		this.protocol = protocol;
		this.fcs = fcs;
		this.initStates();
	}
	
	protected void initStates() {
		states.put(MATCH_ADDRESS_FIELD_STATE, new MatchAddressFieldState());
		states.put(MATCH_CONTROL_FIELD_STATE, new MatchControlFieldState());
		states.put(MATCH_PROTOCOL_ONE_OCTET_FIELD_STATE, new MatchProtocolOneOctetFieldState());
		states.put(MATCH_PROTOCOL_TWO_OCTET_FIELD_STATE, new MatchProtocolTwoOctetFieldState());		
		states.put(PARSE_VALID_MESSAGE_STATE, new ParseValidMessageState());
		states.put(READ_UNTIL_END_FLAG_STATE, new ReadUntilEndingFlagState());
		states.put(READ_UNTIL_FIRST_MATCHING_FLAG_STATE, new ReadUntilFirstMatchingFlagState());
		states.put(SEPARATE_INFORMATION_FROM_CHECKSUM_STATE, new SeparateInformationFromChecksumState());
		states.put(END_OF_STREAM_STATE, new EndOfStreamState());
	}

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParsingContext(new ParsingContextConfig(new EscapeDecoder(), protocol, fcs), inputStream);

		this.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);

		return new ParserResult(this.context.result().getRemaining(), this.context.result().getMessages());
	}

	@Override
	public void setState(State state) {
		try {
			IParsingState newState = states.get(state);
			newState.doAction(this, context);
		} catch (EndOfStreamException e) {
			this.setState(END_OF_STREAM_STATE);
		}
	}

}
