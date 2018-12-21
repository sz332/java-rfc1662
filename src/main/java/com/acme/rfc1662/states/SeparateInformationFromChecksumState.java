package com.acme.rfc1662.states;

import java.util.Arrays;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

/**
 * 
 *
 */
public class SeparateInformationFromChecksumState implements IParsingState {

	final byte[] data;

	public SeparateInformationFromChecksumState(byte[] data) {
		this.data = data;
	}

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {
		int fcsLength = context.config().fcsLengthInBytes();

		if (data.length < fcsLength) {
			machine.setState(new ReadUntilFirstMatchingFlagState());
			return;
		}
		
		context.packetInformation().setInformation(Arrays.copyOf(data, data.length - fcsLength));

		byte[] checksum = Arrays.copyOfRange(data, data.length - fcsLength, data.length);

		int expectedChecksum = byteToInt(checksum);
		int calculatedChecksum = context.config().getFcsCalculator().calculate(context.packetInformation());

		if (expectedChecksum == calculatedChecksum) {
			context.packetInformation().setFcs(calculatedChecksum);
			machine.setState(new ParseValidMessageState());
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
