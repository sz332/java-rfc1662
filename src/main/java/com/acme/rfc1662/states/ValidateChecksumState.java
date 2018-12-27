package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_ADDRESS_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class ValidateChecksumState implements IParsingState {

    private static final int ADDRESS_LENGTH = 1;
    private static final int CONTROL_LENGTH = 1;

    @Override
    public void doAction(final IParsingStateMachine machine, final IParsingContext context) {

        final IParsingContextConfig config = context.config();

        final ByteArrayInputStream is = context.packetInformation().getMessageAsStream();
        is.mark(0);

        final int fcsLength = config.getFcs().lengthInBytes();
        final int protocolLength = config.getProtocol().lengthInBytes();
        final int minimalLength = ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength + fcsLength;

        if (is.available() < minimalLength) {
            machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE, context);
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
            final byte[] information = Arrays.copyOfRange(messageWithoutFcs,
                    ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength, messageWithoutFcs.length);
            context.packetInformation().setInformation(information);
            machine.setState(MATCH_ADDRESS_FIELD_STATE, context);
        } else {
            machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE, context);
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
