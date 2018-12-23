package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class ParseValidMessageState implements IParsingState{

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {
		context.result().addMessage(context.packetInformation().getInformation());
		machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
	}

}
