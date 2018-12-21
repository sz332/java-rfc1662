package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class MatchAddressFieldState extends AbstractState {

	private static final int FIELD_ADDRESS = 0xFF;

	public MatchAddressFieldState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	public void doAction(IParseStateMachine machine, IParsingContext context) {
		int data = context.getDecoder().read(context.getInputStream());

		if (data == FIELD_ADDRESS) {
			machine.setState(new MatchControlFieldState(packetInformation.setAddress(FIELD_ADDRESS)));
		} else {
			machine.setState(new ReadUntilFirstMatchingFlagState());
		}

	}

}
