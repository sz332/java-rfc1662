package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.MATCH_CONTROL_FIELD_STATE;
import static com.acme.rfc1662.IParsingStateMachine.State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE;

import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchAddressFieldState implements IParsingState {

	private static final int FIELD_ADDRESS = 0xFF;

	public void doAction(IParsingStateMachine machine, IParsingContext context) {

		int data = context.config().getDecoder().read(context.inputStream());

		if (data == FIELD_ADDRESS) {
			context.packetInformation().setAddress(FIELD_ADDRESS);
			machine.setState(MATCH_CONTROL_FIELD_STATE);
		} else {
			machine.setState(READ_UNTIL_FIRST_MATCHING_FLAG_STATE);
		}

	}

}
