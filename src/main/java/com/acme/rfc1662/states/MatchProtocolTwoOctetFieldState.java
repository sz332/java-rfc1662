package com.acme.rfc1662.states;

import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class MatchProtocolTwoOctetFieldState implements IState {

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {

        final int first = tempContext.getMessageAsStream().read();
        final int second = tempContext.getMessageAsStream().read();

        tempContext.setProtocol(new byte[] { (byte) first, (byte) second });

        machine.setState(ParseValidMessageState.class, inputContext, outputContext, tempContext);
    }

}
