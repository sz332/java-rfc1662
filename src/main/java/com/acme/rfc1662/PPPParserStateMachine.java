package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.impl.EscapeDecoder;
import com.acme.rfc1662.impl.FcsCalculator;
import com.acme.rfc1662.impl.ParseContext;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;

public class PPPParserStateMachine implements IParseStateMachine {

	IParsingContext context;
	IParseState currentState;

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParseContext(new EscapeDecoder(), new FcsCalculator(), inputStream, 2, 2);

		try {
			this.setState(new ReadUntilFirstMatchingFlagState());
		} catch (EndOfStreamException e) {
			inputStream.reset();

			byte[] remaining = new byte[inputStream.available()];
			inputStream.read(remaining, 0, remaining.length);

			return new ParserResult(remaining, this.context.result().getMessages());
		}

		return new ParserResult(new byte[0], this.context.result().getMessages());
	}

	@Override
	public void setState(IParseState state) {
		this.currentState = state;
		this.currentState.doAction(this, context);
	}

}
