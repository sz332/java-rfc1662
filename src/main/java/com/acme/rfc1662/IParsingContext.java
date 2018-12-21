package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public interface IParsingContext {

	ByteArrayInputStream getInputStream();

	IParsingContextConfig config();
	IParsingContextResult result();
	IPacketInformation packetInformation();

}
