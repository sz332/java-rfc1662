package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class UnescapeState implements IState {

    private static final int END_OF_STREAM = -1;
    private static final int CONTROL_ESCAPE = 0x7d;

    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext, ITemporaryContext tempContext) {

        final ByteArrayInputStream is = tempContext.getMessageAsStream();
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

        tempContext.setMessageAsStream(new ByteArrayInputStream(bos.toByteArray()));

        // packet information contains now the unescaped message

        machine.setState(ValidateChecksumState.class, inputContext, outputContext, tempContext);
    }
}
