package com.acme.rfc1662.enums;

public enum Protocol {

	ONE_OCTET(1, new byte[] { 0x01 }), TWO_OCTET(2, new byte[] { 0x01, 0x02 });

	byte[] identifier;
	int lengthInBytes;

	Protocol(int lengthInBytes, byte[] identifier) {
		this.lengthInBytes = lengthInBytes;
		this.identifier = identifier;
	}

	public int lengthInBytes() {
		return lengthInBytes;
	}

	public byte[] identifier() {
		return identifier;
	}

}
