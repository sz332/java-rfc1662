package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.acme.rfc1662.impl.EscapeDecoder;
import com.acme.rfc1662.impl.FcsCalculator;
import com.acme.rfc1662.impl.ParsingContext;
import com.acme.rfc1662.impl.ParsingContextConfig;
import com.acme.rfc1662.states.MatchAddressFieldState;
import com.acme.rfc1662.states.MatchControlFieldState;
import com.acme.rfc1662.states.MatchProtocolFieldState;
import com.acme.rfc1662.states.ParseValidMessageState;
import com.acme.rfc1662.states.ReadUntilEndingFlagState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;
import com.acme.rfc1662.states.SeparateInformationFromChecksumState;
import com.acme.rfc1662.states.UnknownProtocolLengthState;

public class PPPParserStateMachine implements IParseStateMachine {

	IPacketInformation packetInformation;
	IParsingContext context;
	IParsingState currentState;
	Map<State, IParsingState> states = new HashMap<>();
	
	public PPPParserStateMachine() {
		states.put(State.MatchAddressFieldState, new MatchAddressFieldState());
		states.put(State.MatchControlFieldState, new MatchControlFieldState());
		states.put(State.MatchProtocolFieldState, new MatchProtocolFieldState());
		states.put(State.ParseValidMessageState, new ParseValidMessageState());
		states.put(State.ReadUntilEndingFlagState, new ReadUntilEndingFlagState());
		states.put(State.ReadUntilFirstMatchingFlagState, new ReadUntilFirstMatchingFlagState());
		states.put(State.SeparateInformationFromChecksumState, new SeparateInformationFromChecksumState());
		states.put(State.UnknownProtocolLengthState, new UnknownProtocolLengthState());
	}

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParsingContext(new ParsingContextConfig(new EscapeDecoder(), new FcsCalculator(), 2, 2), inputStream);

		try {
			this.setState(State.ReadUntilFirstMatchingFlagState);
		} catch (EndOfStreamException e) {
			
			// FIXME using exception for control, yuck...
			
			inputStream.reset();

			byte[] remaining = new byte[inputStream.available()];
			inputStream.read(remaining, 0, remaining.length);

			return new ParserResult(remaining, this.context.result().getMessages());
		}

		return new ParserResult(new byte[0], this.context.result().getMessages());
	}

	@Override
	public void setState(State state) {
		this.currentState = states.get(state);
		this.currentState.doAction(this, context);
	}

}
