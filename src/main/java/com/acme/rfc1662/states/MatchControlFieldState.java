package com.acme.rfc1662.states;

import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class MatchControlFieldState implements IState {

    private static final int FIELD_CONTROL = 0x3;

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {
        final int data = tempContext.getMessageAsStream().read();

        if (data == FIELD_CONTROL) {
            if (inputContext.config().getProtocol().lengthInBytes() == 1) {
                machine.setState(MatchProtocolOneOctetFieldState.class);
            } else {
                machine.setState(MatchProtocolTwoOctetFieldState.class);
            }

        } else {
            machine.setState(ReadUntilFirstMatchingFlagState.class);
        }
    }

}
