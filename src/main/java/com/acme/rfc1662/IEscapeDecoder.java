package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public interface IEscapeDecoder {

	int read(ByteArrayInputStream inputStream) throws EndOfStreamException;
	
}
