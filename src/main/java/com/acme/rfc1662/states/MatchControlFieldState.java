package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class MatchControlFieldState implements IParsingState {

    private static final int FIELD_CONTROL = 0x3;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {
        final int data = context.packetInformation().getMessageAsStream().read();

        if (data == FIELD_CONTROL) {
            if (context.config().getProtocol().lengthInBytes() == 1) {
                machine.setState(MatchProtocolOneOctetFieldState.class, context);
            } else {
                machine.setState(MatchProtocolTwoOctetFieldState.class, context);
            }

        } else {
            machine.setState(ReadUntilFirstMatchingFlagState.class, context);
        }
    }

}
