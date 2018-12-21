package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class ParseValidMessageState implements IParsingState{

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {
		context.result().addMessage(context.packetInformation().getInformation());
		machine.setState(new ReadUntilFirstMatchingFlagState());
	}

}
