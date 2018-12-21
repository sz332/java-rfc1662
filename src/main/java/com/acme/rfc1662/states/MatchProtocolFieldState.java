package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParseStateMachine.State.READ_UNTIL_END_FLAG_STATE;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParseStateMachine.State;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchProtocolFieldState implements IParsingState{

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {

		ByteArrayInputStream is = context.inputStream();

		if (context.config().protocolFieldLengthInBytes() == 1) {
			readOneByte(machine, context, is);
		} else {
			readTwoBytes(machine, context, is);
		}
	}

	private void readOneByte(IParseStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int data = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) data });
		machine.setState(READ_UNTIL_END_FLAG_STATE);
	}

	private void readTwoBytes(IParseStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int first = context.config().getDecoder().read(is);
		int second = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) first, (byte) second });
		machine.setState(State.READ_UNTIL_END_FLAG_STATE);
	}

}
