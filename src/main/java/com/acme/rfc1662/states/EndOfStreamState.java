package com.acme.rfc1662.states;

import com.acme.rfc1662.IParseContext;
import com.acme.rfc1662.IParseState;
import com.acme.rfc1662.IParseStateMachine;

public class EndOfStreamState implements IParseState{

	public void doAction(IParseStateMachine machine, IParseContext context) {
		System.out.println("Stream ended");
	}
	
}
