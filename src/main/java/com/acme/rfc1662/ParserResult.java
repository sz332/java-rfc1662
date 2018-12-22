package com.acme.rfc1662;

import java.util.List;

public class ParserResult {

	private final byte[] remaining;
	private final List<byte[]> messages;

	public ParserResult(byte[] remaining, List<byte[]> packets) {
		this.remaining = remaining;
		this.messages = packets;
	}

	public List<byte[]> messages() {
		return messages;
	}

	public byte[] remaining() {
		return remaining;
	}

}
