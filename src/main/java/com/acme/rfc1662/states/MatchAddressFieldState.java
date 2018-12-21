package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParseStateMachine.State;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchAddressFieldState implements IParsingState {

	private static final int FIELD_ADDRESS = 0xFF;

	public void doAction(IParseStateMachine machine, IParsingContext context) {

		int data = context.config().getDecoder().read(context.inputStream());

		if (data == FIELD_ADDRESS) {
			context.packetInformation().setAddress(FIELD_ADDRESS);
			machine.setState(State.MatchControlFieldState);
		} else {
			machine.setState(State.ReadUntilFirstMatchingFlagState);
		}

	}

}
