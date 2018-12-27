package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_END_FLAG_STATE;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class ReadUntilFirstMatchingFlagState implements IParsingState {

    private static final int FIELD_FLAG = 0x7E;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        int result;

        do {
            context.inputStream().mark(0);
            result = context.config().getReader().read(context.inputStream());
        } while (result != FIELD_FLAG);

        machine.setState(READ_UNTIL_END_FLAG_STATE, context);
    }

}
