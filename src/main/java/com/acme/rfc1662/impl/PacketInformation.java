package com.acme.rfc1662.impl;

import com.acme.rfc1662.IPacketInformation;

public class PacketInformation implements IPacketInformation {

	int address;
	int control;
	byte[] protocol;
	byte[] information;
	int fcs;

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public IPacketInformation setAddress(int address) {
		this.address = address;
		return this;
	}

	@Override
	public int getControl() {
		return control;
	}

	@Override
	public IPacketInformation setControl(int control) {
		this.control = control;
		return this;
	}

	@Override
	public byte[] getProtocol() {
		return protocol;
	}

	@Override
	public IPacketInformation setProtocol(byte[] protocol) {
		this.protocol = protocol;
		return this;
	}

	@Override
	public byte[] getInformation() {
		return information;
	}

	@Override
	public IPacketInformation setInformation(byte[] information) {
		this.information = information;
		return this;
	}
	
	@Override
	public int getFcs() {
		return fcs;
	}
	
	@Override
	public IPacketInformation setFcs(int fcs) {
		this.fcs = fcs;
		return this;
	}

}
