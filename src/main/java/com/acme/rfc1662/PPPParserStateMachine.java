package com.acme.rfc1662;

import static com.acme.rfc1662.IParseStateMachine.State.END_OF_STREAM_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.MATCH_ADDRESS_FIELD_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.MATCH_CONTROL_FIELD_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.MATCH_PROTOCOL_FIELD_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.PARSE_VALID_MESSAGE_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.READ_UNTIL_END_FLAG_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;
import static com.acme.rfc1662.IParseStateMachine.State.SEPARATE_INFORMATION_FROM_CHECKSUM_STATE;

import java.io.ByteArrayInputStream;
import java.util.EnumMap;

import com.acme.rfc1662.impl.EscapeDecoder;
import com.acme.rfc1662.impl.FcsCalculator;
import com.acme.rfc1662.impl.ParsingContext;
import com.acme.rfc1662.impl.ParsingContextConfig;
import com.acme.rfc1662.states.EndOfStreamState;
import com.acme.rfc1662.states.MatchAddressFieldState;
import com.acme.rfc1662.states.MatchControlFieldState;
import com.acme.rfc1662.states.MatchProtocolFieldState;
import com.acme.rfc1662.states.ParseValidMessageState;
import com.acme.rfc1662.states.ReadUntilEndingFlagState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;
import com.acme.rfc1662.states.SeparateInformationFromChecksumState;

public class PPPParserStateMachine implements IParseStateMachine {

	IParsingContext context;

	EnumMap<State, IParsingState> states = new EnumMap<>(State.class);

	public PPPParserStateMachine() {
		states.put(MATCH_ADDRESS_FIELD_STATE, new MatchAddressFieldState());
		states.put(MATCH_CONTROL_FIELD_STATE, new MatchControlFieldState());
		states.put(MATCH_PROTOCOL_FIELD_STATE, new MatchProtocolFieldState());
		states.put(PARSE_VALID_MESSAGE_STATE, new ParseValidMessageState());
		states.put(READ_UNTIL_END_FLAG_STATE, new ReadUntilEndingFlagState());
		states.put(READ_UNTIL_FIRST_MATCHING_FLAG_STATE, new ReadUntilFirstMatchingFlagState());
		states.put(SEPARATE_INFORMATION_FROM_CHECKSUM_STATE, new SeparateInformationFromChecksumState());
		states.put(END_OF_STREAM_STATE, new EndOfStreamState());
	}

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParsingContext(new ParsingContextConfig(new EscapeDecoder(), new FcsCalculator(), 2, 2), inputStream);

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
