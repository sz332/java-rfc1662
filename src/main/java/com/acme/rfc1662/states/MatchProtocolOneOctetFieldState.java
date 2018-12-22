package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_END_FLAG_STATE;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;

public class MatchProtocolOneOctetFieldState implements IParsingState {

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		ByteArrayInputStream is = context.inputStream();

		int data = context.config().getDecoder().read(is);
		context.packetInformation().setProtocol(new byte[] { (byte) data });
		machine.setState(READ_UNTIL_END_FLAG_STATE);
	}

}
