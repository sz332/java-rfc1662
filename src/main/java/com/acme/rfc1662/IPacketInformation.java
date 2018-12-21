package com.acme.rfc1662;

public interface IPacketInformation {

	int getAddress();

	void setAddress(int address);

	int getControl();

	void setControl(int control);

	byte[] getProtocol();

	void setProtocol(byte[] protocol);

	byte[] getInformation();

	void setInformation(byte[] information);

	void setCombinedData(byte[] data);
	
	byte[] getCombinedData();

}