package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_END_FLAG_STATE;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingStateMachine.State;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchProtocolFieldState implements IParsingState{

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		ByteArrayInputStream is = context.inputStream();

		if (context.config().getProtocol().lengthInBytes() == 1) {
			readOneByte(machine, context, is);
		} else {
			readTwoBytes(machine, context, is);
		}
	}

	private void readOneByte(IParsingStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int data = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) data });
		machine.setState(READ_UNTIL_END_FLAG_STATE);
	}

	private void readTwoBytes(IParsingStateMachine machine, IParsingContext context, ByteArrayInputStream is) {
		int first = context.config().getDecoder().read(is);
		int second = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) first, (byte) second });
		machine.setState(State.READ_UNTIL_END_FLAG_STATE);
	}

}
