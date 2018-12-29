package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class MatchProtocolTwoOctetFieldState implements IParsingState {

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final int first = context.packetInformation().getMessageAsStream().read();
        final int second = context.packetInformation().getMessageAsStream().read();

        context.packetInformation().setProtocol(new byte[] { (byte) first, (byte) second });

        machine.setState(ParseValidMessageState.class, context);
    }

}
