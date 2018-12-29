package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class ReadUntilEndingFlagState implements IState {

    private static final int FIELD_FLAG = 0x7E;

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int data;

        do {
            data = inputContext.config().getReader().read(inputContext.inputStream());

            if (data != FIELD_FLAG) {
                bos.write(data);
            }

        } while (data != FIELD_FLAG);

        inputContext.inputStream().mark(0);

        tempContext.setMessageAsStream(new ByteArrayInputStream(bos.toByteArray()));

        machine.setState(UnescapeState.class);
    }

}
