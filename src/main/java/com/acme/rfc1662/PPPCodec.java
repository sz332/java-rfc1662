package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.impl.PPPEncoder;
import com.acme.rfc1662.impl.ParsingStateMachine;

public class PPPCodec {

	Protocol protocol;
	FrameCheckSequence fcs;
	
	public PPPCodec(Protocol protocol, FrameCheckSequence fcs) {
		this.protocol = protocol;
		this.fcs = fcs;
	}

	public ParserResult decode(ByteArrayInputStream is) {
		ParsingStateMachine sm = new ParsingStateMachine(protocol, fcs);
		return sm.parse(is);
	}

	public byte[] encode(byte[] content) {
		PPPEncoder encoder = new PPPEncoder(protocol, fcs);
		return encoder.encode(content);
	}

}
