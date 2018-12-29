package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class UnescapeState implements IParsingState {

    private static final int END_OF_STREAM = -1;
    private static final int CONTROL_ESCAPE = 0x7d;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final ByteArrayInputStream is = context.packetInformation().getMessageAsStream();
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while (is.available() > 0) {
            int data = is.read();

            if (data == CONTROL_ESCAPE) {
                data = is.read();

                if (data == END_OF_STREAM) {
                    break;
                }

                bos.write(data ^ 0x20);
            } else {
                bos.write(data);
            }
        }

        context.packetInformation().setMessageAsStream(new ByteArrayInputStream(bos.toByteArray()));

        // packet information contains now the unescaped message

        machine.setState(ValidateChecksumState.class, context);
    }
}
