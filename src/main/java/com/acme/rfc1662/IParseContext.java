package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IParseContext {

	ByteArrayInputStream getInputStream();

	IEscapeDecoder getDecoder();

	IFcsCalculator getFcsCalculator();

	int protocolFieldLengthInBytes();

	int fcsLengthInBytes();

	void addMessage(byte[] data);

	List<byte[]> getMessages();

}
