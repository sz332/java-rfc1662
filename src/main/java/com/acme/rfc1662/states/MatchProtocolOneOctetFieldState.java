package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingStateMachine.State;

public class MatchProtocolOneOctetFieldState implements IParsingState {

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final int data = context.packetInformation().getMessageAsStream().read();

        context.packetInformation().setProtocol(new byte[] { (byte) data });

        machine.setState(State.PARSE_VALID_MESSAGE_STATE, context);
    }

}
