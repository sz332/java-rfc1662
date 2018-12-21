package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class ParseValidMessageState extends AbstractState {

	public ParseValidMessageState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	@Override
	public void doAction(IParseStateMachine machine, IParsingContext context) {
		context.result().addMessage(packetInformation.getInformation());
		machine.setState(new ReadUntilFirstMatchingFlagState());
	}

}
