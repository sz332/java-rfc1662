package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class MatchProtocolFieldState extends AbstractState {

	public MatchProtocolFieldState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	public void doAction(IParseStateMachine machine, IParsingContext context) {

		ByteArrayInputStream is = context.getInputStream();

		if (context.config().protocolFieldLengthInBytes() == 1) {
			readOneByte(machine, context, is);
		} else if (context.config().protocolFieldLengthInBytes() == 2) {
			readTwoBytes(machine, context, is);
		} else {
			machine.setState(new UnknownProtocolLengthState());
		}

	}

	private void readOneByte(IParseStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int data = context.config().getDecoder().read(is);
		machine.setState(new ReadUntilEndingFlagState(packetInformation.setProtocol(new byte[] { (byte) data })));
	}

	private void readTwoBytes(IParseStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int first = context.config().getDecoder().read(is);
		int second = context.config().getDecoder().read(is);

		machine.setState(new ReadUntilEndingFlagState(packetInformation.setProtocol(new byte[] { (byte) first, (byte) second })));
	}

}
