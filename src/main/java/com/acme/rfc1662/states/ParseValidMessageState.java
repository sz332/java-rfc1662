package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class ParseValidMessageState extends AbstractState {

	public ParseValidMessageState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	@Override
	public void doAction(IParseStateMachine machine, IParseContext context) {
		context.addMessage(packetInformation.getInformation());
		machine.setState(new ReadUntilFirstMatchingFlagState());
	}

}
