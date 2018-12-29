package com.acme.rfc1662.states;

import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class MatchAddressFieldState implements IState {

    private static final int FIELD_ADDRESS = 0xFF;

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {

        final int data = tempContext.getMessageAsStream().read();

        if (data == FIELD_ADDRESS) {
            machine.setState(MatchControlFieldState.class, inputContext, outputContext, tempContext);
        } else {
            machine.setState(ReadUntilFirstMatchingFlagState.class, inputContext, outputContext, tempContext);
        }

    }

}
