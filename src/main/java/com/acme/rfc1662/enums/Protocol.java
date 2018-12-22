package com.acme.rfc1662.enums;

public enum Protocol {

	DEFAULT(2);
	
	int lengthInBytes;
	
	Protocol(int lengthInBytes){
		this.lengthInBytes = lengthInBytes;
	}
	
	int lengthInBytes() {
		return lengthInBytes;
	}
	
}
