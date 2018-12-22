package com.acme.rfc1662.states;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingStateMachine.State;

public class MatchProtocolTwoOctetFieldState implements IParsingState {

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		ByteArrayInputStream is = context.inputStream();

		int first = context.config().getDecoder().read(is);
		int second = context.config().getDecoder().read(is);
		
		context.packetInformation().setProtocol(new byte[] { (byte) first, (byte) second });

		machine.setState(State.READ_UNTIL_END_FLAG_STATE);
	}

}
