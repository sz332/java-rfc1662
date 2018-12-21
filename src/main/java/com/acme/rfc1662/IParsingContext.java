package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public interface IParsingContext {

	ByteArrayInputStream inputStream();

	IParsingContextConfig config();
	IParsingContextResult result();
	IPacketInformation packetInformation();

}
