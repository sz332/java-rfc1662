package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class EndOfStreamState implements IParsingState{

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {
		context.getInputStream().reset();

		byte[] remaining = new byte[context.getInputStream().available()];
		context.getInputStream().read(remaining, 0, remaining.length);
		
		context.result().setRemaining(remaining);
	}

}
