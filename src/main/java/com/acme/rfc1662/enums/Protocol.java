package com.acme.rfc1662.enums;

public enum Protocol {

	ONE_OCTET(1), TWO_OCTET(2);
	
	int lengthInBytes;
	
	Protocol(int lengthInBytes){
		this.lengthInBytes = lengthInBytes;
	}
	
	public int lengthInBytes() {
		return lengthInBytes;
	}
	
}
