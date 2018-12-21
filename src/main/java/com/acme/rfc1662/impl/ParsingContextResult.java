package com.acme.rfc1662.impl;

import java.util.ArrayList;
import java.util.List;

import com.acme.rfc1662.IParsingContextResult;

public class ParsingContextResult implements IParsingContextResult{

	private final List<byte[]> messages = new ArrayList<>();
	
	@Override
	public void addMessage(byte[] data) {
		messages.add(data);
	}

	@Override
	public List<byte[]> getMessages() {
		return messages;
	}
	
}
