package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseState;
import com.acme.rfc1662.PacketInformation;

public abstract class AbstractState implements IParseState{

	protected PacketInformation packetInformation;
	
	public AbstractState(PacketInformation packetInformation) {
		this.packetInformation = packetInformation;
	}
	
}
