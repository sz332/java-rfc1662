package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class ParseValidMessageState implements IParsingState {

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {
        context.result().addMessage(context.packetInformation().getInformation());
        machine.setState(ReadUntilFirstMatchingFlagState.class, context);
    }

}
