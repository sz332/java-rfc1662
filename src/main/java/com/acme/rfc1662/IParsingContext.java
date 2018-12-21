package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.util.List;

// FIXME separate this into multiple classes (inputStream, config, result)
public interface IParsingContext {

	ByteArrayInputStream getInputStream();

	IEscapeDecoder getDecoder();

	IFcsCalculator getFcsCalculator();

	int protocolFieldLengthInBytes();

	int fcsLengthInBytes();

	void addMessage(byte[] data);

	List<byte[]> getMessages();

}
