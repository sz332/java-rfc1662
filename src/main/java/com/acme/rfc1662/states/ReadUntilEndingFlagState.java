package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class ReadUntilEndingFlagState implements IParsingState {

    private static final int FIELD_FLAG = 0x7E;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int data;

        do {
            data = context.config().getReader().read(context.inputStream());

            if (data != FIELD_FLAG) {
                bos.write(data);
            }

        } while (data != FIELD_FLAG);

        context.inputStream().mark(0);

        context.packetInformation().setMessageAsStream(new ByteArrayInputStream(bos.toByteArray()));

        machine.setState(UnescapeState.class, context);
    }

}
