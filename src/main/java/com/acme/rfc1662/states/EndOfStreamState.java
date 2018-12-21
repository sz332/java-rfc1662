package com.acme.rfc1662.states;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParseStateMachine;

public class EndOfStreamState implements IParsingState{

	public void doAction(IParseStateMachine machine, IParsingContext context) {
		System.out.println("Stream ended");
	}
	
}
