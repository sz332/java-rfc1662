package com.acme.rfc1662.states;

import java.util.Arrays;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

/**
 * 
 *
 */
public class SeparateInformationFromChecksumState extends AbstractState {

	final byte[] data;

	public SeparateInformationFromChecksumState(PacketInformation packetInformation, byte[] data) {
		super(packetInformation);
		this.data = data;
	}

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {
		int fcsLength = context.fcsLengthInBytes();

		if (data.length < fcsLength) {
			machine.setState(new ReadUntilFirstMatchingFlagState());
			return;
		}
		
		this.packetInformation.setInformation(Arrays.copyOf(data, data.length - fcsLength));

		byte[] checksum = Arrays.copyOfRange(data, data.length - fcsLength, data.length);

		int expectedChecksum = byteToInt(checksum);
		int calculatedChecksum = context.getFcsCalculator().calculate(this.packetInformation);

		if (expectedChecksum == calculatedChecksum) {
			machine.setState(new ParseValidMessageState(packetInformation.setFcs(calculatedChecksum)));
		} else {
			machine.setState(new ReadUntilFirstMatchingFlagState());
		}

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
