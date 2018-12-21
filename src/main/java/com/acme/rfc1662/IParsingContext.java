package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

// FIXME separate this into multiple classes (inputStream, config, result)
public interface IParsingContext {

	ByteArrayInputStream getInputStream();

	IEscapeDecoder getDecoder();

	IFcsCalculator getFcsCalculator();

	int protocolFieldLengthInBytes();

	int fcsLengthInBytes();

	IParsingContextResult result();

}
