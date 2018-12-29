package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IContextConfig;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;

public class ValidateChecksumState implements IState {

    private static final int ADDRESS_LENGTH = 1;
    private static final int CONTROL_LENGTH = 1;

    // FIXME refactor method because it contains too much code
    @Override
    public void doAction(IStateMachine machine, IInputContext inputContext, IOutputContext outputContext,
            ITemporaryContext tempContext) {

        final IContextConfig config = inputContext.config();

        final ByteArrayInputStream is = tempContext.getMessageAsStream();
        is.mark(0);

        final int fcsLength = config.getFcs().lengthInBytes();
        final int protocolLength = config.getProtocol().lengthInBytes();
        final int minimalLength = ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength + fcsLength;

        if (is.available() < minimalLength) {
            machine.setState(ReadUntilFirstMatchingFlagState.class);
            return;
        }

        final byte[] messageWithoutFcs = new byte[is.available() - fcsLength];
        is.read(messageWithoutFcs, 0, is.available() - fcsLength);

        final byte[] fcs = new byte[fcsLength];
        is.read(fcs, 0, fcsLength);

        is.reset();

        final int expectedChecksum = byteToInt(fcs);
        final int calculatedChecksum = config.getFcs().calculator().calculate(messageWithoutFcs);

        if (expectedChecksum == calculatedChecksum) {
            final byte[] information = Arrays.copyOfRange(messageWithoutFcs, ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength,
                    messageWithoutFcs.length);
            tempContext.setInformation(information);
            machine.setState(MatchAddressFieldState.class);
        } else {
            machine.setState(ReadUntilFirstMatchingFlagState.class);
        }

    }

    // FIXME handle various fcs sizes, move it into enum
    private int byteToInt(final byte[] data) {
        int i = 0;
        i |= data[1] & 0xFF;
        i <<= 8;
        i |= data[0] & 0xFF;
        return i;
    }

}
