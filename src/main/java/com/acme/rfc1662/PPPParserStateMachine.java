package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.acme.rfc1662.states.UnknownProtocolLengthState;

public class PPPParserStateMachine implements IParseStateMachine {

	private static final Logger logger = LoggerFactory.getLogger(PPPParserStateMachine.class);

	IParsingContext context;

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
		states.put(State.EndOfStreamState, new EndOfStreamState());
	}

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParsingContext(new ParsingContextConfig(new EscapeDecoder(), new FcsCalculator(), 2, 2), inputStream);

		this.setState(State.ReadUntilFirstMatchingFlagState);

		return new ParserResult(this.context.result().getRemaining(), this.context.result().getMessages());
	}

	@Override
	public void setState(State state) {

		if (!states.containsKey(state)) {
			logger.error("No class is assigned to state {}, exiting...", state);
			return;
		}

		try {
			IParsingState newState = states.get(state);
			newState.doAction(this, context);
		} catch (EndOfStreamException e) {
			this.setState(State.EndOfStreamState);
		}
	}

}
