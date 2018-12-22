package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public interface IPPPEncoder {

	public byte[] encode(ByteArrayInputStream is);
	
}
