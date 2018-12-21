package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseState;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class ReadUntilFirstMatchingFlagState implements IParseState {

	private static final int FIELD_FLAG = 0x7E;

	public ReadUntilFirstMatchingFlagState() {
	}

	public void doAction(IParseStateMachine machine, IParsingContext context) {

		int result = -1;

		do {
			context.getInputStream().mark(0);
			result = context.config().getDecoder().read(context.getInputStream());
		} while (result != FIELD_FLAG);

		machine.setState(new MatchAddressFieldState(new PacketInformation()));
	}

}
