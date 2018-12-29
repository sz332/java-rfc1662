package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class MatchAddressFieldState implements IParsingState {

    private static final int FIELD_ADDRESS = 0xFF;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final int data = context.packetInformation().getMessageAsStream().read();

        if (data == FIELD_ADDRESS) {
            machine.setState(MatchControlFieldState.class, context);
        } else {
            machine.setState(ReadUntilFirstMatchingFlagState.class, context);
        }

    }

}
