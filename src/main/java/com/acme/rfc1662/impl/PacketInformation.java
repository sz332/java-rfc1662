package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IPacketInformation;

public class PacketInformation implements IPacketInformation {

	byte[] protocol;
	byte[] information;

	ByteArrayInputStream message;

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
		this.information = information;
	}

	@Override
	public ByteArrayInputStream getMessageAsStream() {
		return message;
	}

	@Override
	public void setMessageAsStream(ByteArrayInputStream message) {
		this.message = message;
	}

}
