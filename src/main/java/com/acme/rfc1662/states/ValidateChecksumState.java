package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingStateMachine.State;

public class ValidateChecksumState implements IParsingState {

	private static final int ADDRESS_LENGTH = 1;
	private static final int CONTROL_LENGTH = 1;

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		IParsingContextConfig config = context.config();

		ByteArrayInputStream is = context.packetInformation().getMessageAsStream();
		is.mark(0);

		int fcsLength = config.getFcs().lengthInBytes();
		int protocolLength = config.getProtocol().lengthInBytes();
		int minimalLength = ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength + fcsLength;

		if (is.available() < minimalLength) {
			machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
			return;
		}

		byte[] messageWithoutFcs = new byte[is.available() - fcsLength];
		is.read(messageWithoutFcs, 0, is.available() - fcsLength);

		byte[] fcs = new byte[fcsLength];
		is.read(fcs, 0, fcsLength);

		is.reset();

		int expectedChecksum = byteToInt(fcs);
		int calculatedChecksum = config.getFcs().calculator().calculate(messageWithoutFcs);

		if (expectedChecksum == calculatedChecksum) {
			byte[] information = Arrays.copyOfRange(messageWithoutFcs, ADDRESS_LENGTH + CONTROL_LENGTH + protocolLength, messageWithoutFcs.length);
			context.packetInformation().setInformation(information);
			machine.setState(State.MATCH_ADDRESS_FIELD_STATE);
		} else {
			machine.setState(State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
		}

	}

	// FIXME handle various fcs sizes, move it into enum
	private int byteToInt(byte[] data) {
		int i = 0;
		i |= data[1] & 0xFF;
		i <<= 8;
		i |= data[0] & 0xFF;
		return i;
	}

}
