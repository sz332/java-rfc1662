package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.impl.PPPEncoder;
import com.acme.rfc1662.impl.ParsingStateMachine;

public class PPPCodec {

	public ParserResult decode(ByteArrayInputStream is) {
		ParsingStateMachine sm = new ParsingStateMachine();
		return sm.parse(is);
	}

	public byte[] encode(ByteArrayInputStream is) {
		PPPEncoder encoder = new PPPEncoder();
		return encoder.encode(is);
	}

}
