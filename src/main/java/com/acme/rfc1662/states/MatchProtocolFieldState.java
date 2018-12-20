package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParseContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class MatchProtocolFieldState extends AbstractState {

	public MatchProtocolFieldState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	public void doAction(IParseStateMachine machine, IParseContext context) {

		ByteArrayInputStream is = context.getInputStream();

		if (context.protocolFieldLengthInBytes() == 1) {
			readOneByte(machine, context, is);
		} else if (context.protocolFieldLengthInBytes() == 2) {
			readTwoBytes(machine, context, is);
		} else {
			machine.setState(new UnknownProtocolLengthState());
		}

	}

	private void readOneByte(IParseStateMachine machine, IParseContext context, ByteArrayInputStream is) {
		int data = context.getDecoder().read(is);
		machine.setState(new ReadUntilEndingFlagState(packetInformation.setProtocol(new byte[] { (byte) data })));
	}

	private void readTwoBytes(IParseStateMachine machine, IParseContext context, ByteArrayInputStream is) {
		int first = context.getDecoder().read(is);
		int second = context.getDecoder().read(is);

		machine.setState(new ReadUntilEndingFlagState(packetInformation.setProtocol(new byte[] { (byte) first, (byte) second })));
	}

}
