package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class MatchControlFieldState extends AbstractState {

	private static final int FIELD_CONTROL = 0x3;

	public MatchControlFieldState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	public void doAction(IParseStateMachine machine, IParseContext context) {
		int data = context.getDecoder().read(context.getInputStream());

		if (data == FIELD_CONTROL) {
			machine.setState(new MatchProtocolFieldState(packetInformation.setControl(FIELD_CONTROL)));
		} else {
			machine.setState(new ReadUntilFirstMatchingFlagState());
		}
	}

}
