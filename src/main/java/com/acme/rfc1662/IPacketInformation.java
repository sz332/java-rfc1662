package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public interface IPacketInformation {

	byte[] getProtocol();

	void setProtocol(byte[] protocol);

	byte[] getInformation();

	void setInformation(byte[] information);

	/**
	 * The whole message between the two flags
	 * @param data
	 */
	void setMessageAsStream(ByteArrayInputStream message);

	ByteArrayInputStream getMessageAsStream();

}