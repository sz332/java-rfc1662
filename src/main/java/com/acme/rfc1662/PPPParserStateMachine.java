package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.impl.EscapeDecoder;
import com.acme.rfc1662.impl.FcsCalculator;
import com.acme.rfc1662.impl.ParsingContext;
import com.acme.rfc1662.impl.ParsingContextConfig;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;

public class PPPParserStateMachine implements IParseStateMachine {

	IPacketInformation packetInformation;
	IParsingContext context;
	IParsingState currentState;

	public ParserResult parse(ByteArrayInputStream inputStream) {

		inputStream.mark(0);

		this.context = new ParsingContext(new ParsingContextConfig(new EscapeDecoder(), new FcsCalculator(), 2, 2), inputStream);

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
	public void setState(IParsingState state) {
		this.currentState = state;
		this.currentState.doAction(this, context);
	}

}
