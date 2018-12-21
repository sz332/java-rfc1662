package com.acme.rfc1662.impl;

import java.util.ArrayList;
import java.util.List;

import com.acme.rfc1662.IParsingContextResult;

public class ParsingContextResult implements IParsingContextResult {

	private List<byte[]> messages = new ArrayList<>();
	private byte[] remaining = new byte[0];

	@Override
	public void addMessage(byte[] data) {
		messages.add(data);
	}

	@Override
	public List<byte[]> getMessages() {
		return messages;
	}

	public void setRemaining(byte[] remaining) {
		this.remaining = remaining;
	}

	public byte[] getRemaining() {
		return remaining;
	}

}
