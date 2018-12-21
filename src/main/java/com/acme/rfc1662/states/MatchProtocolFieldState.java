package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchProtocolFieldState implements IParsingState{

	@Override
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
		context.packetInformation().setProtocol(new byte[] { (byte) data });
		machine.setState(new ReadUntilEndingFlagState());
	}

	private void readTwoBytes(IParseStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int first = context.config().getDecoder().read(is);
		int second = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) first, (byte) second });
		machine.setState(new ReadUntilEndingFlagState());
	}

}
