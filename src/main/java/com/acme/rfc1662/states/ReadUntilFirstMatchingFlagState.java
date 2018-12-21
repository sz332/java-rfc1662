package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParseStateMachine.State.MATCH_ADDRESS_FIELD_STATE;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class ReadUntilFirstMatchingFlagState implements IParsingState {

	private static final int FIELD_FLAG = 0x7E;

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {

		int result = -1;

		do {
			context.inputStream().mark(0);
			result = context.config().getDecoder().read(context.inputStream());
		} while (result != FIELD_FLAG);

		machine.setState(MATCH_ADDRESS_FIELD_STATE);
	}

}
