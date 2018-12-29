package com.acme.rfc1662.states;

import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class EndOfStreamState implements IState {

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {
        inputContext.inputStream().reset();

        final byte[] remaining = new byte[inputContext.inputStream().available()];
        inputContext.inputStream().read(remaining, 0, remaining.length);

        outputContext.setRemaining(remaining);
    }

}
