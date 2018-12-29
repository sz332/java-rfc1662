package com.acme.rfc1662.states;

import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class ReadUntilFirstMatchingFlagState implements IState {

    private static final int FIELD_FLAG = 0x7E;

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {

        int result;

        do {
            inputContext.inputStream().mark(0);
            result = inputContext.config().getReader().read(inputContext.inputStream());
        } while (result != FIELD_FLAG);

        machine.setState(ReadUntilEndingFlagState.class, inputContext, outputContext, tempContext);
    }

}
