package com.acme.rfc1662.impl;

import com.acme.rfc1662.IPacketInformation;

public class PacketInformation implements IPacketInformation {

	int address;
	int control;
	byte[] protocol;
	byte[] information;
	int fcs;

	byte[] combinedData;

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public void setAddress(int address) {
		this.address = address;
	}

	@Override
	public int getControl() {
		return control;
	}

	@Override
	public void setControl(int control) {
		this.control = control;
	}

	@Override
	public byte[] getProtocol() {
		return protocol;
	}

	@Override
	public void setProtocol(byte[] protocol) {
		this.protocol = protocol;
	}

	@Override
	public byte[] getInformation() {
		return information;
	}

	@Override
	public void setInformation(byte[] information) {
		this.information = information;	}

	@Override
	public int getFcs() {
		return fcs;
	}

	@Override
	public void setFcs(int fcs) {
		this.fcs = fcs;
	}

	@Override
	public byte[] getCombinedData() {
		return combinedData;
	}
	
	@Override
	public void setCombinedData(byte[] data) {
		this.combinedData = data;
	}
	
}
