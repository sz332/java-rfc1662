package com.acme.rfc1662;

public class PacketInformation {

	int address;
	int control;
	byte[] protocol;
	byte[] information;
	int fcs;

	public int getAddress() {
		return address;
	}

	public PacketInformation setAddress(int address) {
		this.address = address;
		return this;
	}

	public int getControl() {
		return control;
	}

	public PacketInformation setControl(int control) {
		this.control = control;
		return this;
	}

	public byte[] getProtocol() {
		return protocol;
	}

	public PacketInformation setProtocol(byte[] protocol) {
		this.protocol = protocol;
		return this;
	}

	public byte[] getInformation() {
		return information;
	}

	public PacketInformation setInformation(byte[] information) {
		this.information = information;
		return this;
	}
	
	public int getFcs() {
		return fcs;
	}
	
	public PacketInformation setFcs(int fcs) {
		this.fcs = fcs;
		return this;
	}

}
