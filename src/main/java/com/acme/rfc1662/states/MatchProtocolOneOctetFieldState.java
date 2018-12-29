package com.acme.rfc1662.states;

import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class MatchProtocolOneOctetFieldState implements IState {

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {

        final int data = tempContext.getMessageAsStream().read();

        tempContext.setProtocol(new byte[] { (byte) data });

        machine.setState(ParseValidMessageState.class, inputContext, outputContext, tempContext);
    }

}
