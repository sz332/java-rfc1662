package com.acme.rfc1662;

public interface IPacketInformation {

	int getAddress();

	IPacketInformation setAddress(int address);

	int getControl();

	IPacketInformation setControl(int control);

	byte[] getProtocol();

	IPacketInformation setProtocol(byte[] protocol);

	byte[] getInformation();

	IPacketInformation setInformation(byte[] information);

	int getFcs();

	IPacketInformation setFcs(int fcs);

}