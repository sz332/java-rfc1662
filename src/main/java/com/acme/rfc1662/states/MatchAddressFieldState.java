package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_CONTROL_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class MatchAddressFieldState implements IParsingState {

    private static final int FIELD_ADDRESS = 0xFF;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final int data = context.packetInformation().getMessageAsStream().read();

        if (data == FIELD_ADDRESS) {
            machine.setState(MATCH_CONTROL_FIELD_STATE, context);
        } else {
            machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE, context);
        }

    }

}
