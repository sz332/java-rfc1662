package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseStateMachine.State;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class MatchControlFieldState implements IParsingState {

	private static final int FIELD_CONTROL = 0x3;

	public void doAction(IParseStateMachine machine, IParsingContext context) {
		int data = context.config().getDecoder().read(context.inputStream());

		if (data == FIELD_CONTROL) {
			context.packetInformation().setControl(FIELD_CONTROL);
			machine.setState(State.MatchProtocolFieldState);
		} else {
			machine.setState(State.ReadUntilFirstMatchingFlagState);
		}
	}

}
