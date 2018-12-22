package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.PARSE_VALID_MESSAGE_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import java.util.Arrays;

import com.acme.rfc1662.IFCSByteArrayCalculator;
import com.acme.rfc1662.IPacketInformation;
import com.acme.rfc1662.IPacketInformationSerializer;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.impl.PacketInformationSerializer;

public class SeparateInformationFromChecksumState implements IParsingState {

	private final IPacketInformationSerializer serializer = new PacketInformationSerializer();
	
	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		IPacketInformation packetInformation = context.packetInformation();
		IParsingContextConfig config = context.config();

		byte[] data = packetInformation.getCombinedData();

		int fcsLength = config.getProtocol().lengthInBytes();

		if (data.length < fcsLength) {
			machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
			return;
		}

		packetInformation.setInformation(Arrays.copyOf(data, data.length - fcsLength));

		byte[] checksum = Arrays.copyOfRange(data, data.length - fcsLength, data.length);

		int expectedChecksum = byteToInt(checksum);
		int calculatedChecksum = calculateChecksum(config, packetInformation);

		if (expectedChecksum == calculatedChecksum) {
			machine.setState(PARSE_VALID_MESSAGE_STATE);
		} else {
			machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
		}

	}

	private int calculateChecksum(IParsingContextConfig config, IPacketInformation packetInformation) {
		IFCSByteArrayCalculator calculator = config.getFcs().calculator();
		return calculator.calculate(serializer.convertPacketInformation(packetInformation));
	}

	// FIXME check endian in the RFC
	public int byteToInt(byte[] data) {
		int i = 0;
		i |= data[1] & 0xFF;
		i <<= 8;
		i |= data[0] & 0xFF;
		return i;
	}

}
