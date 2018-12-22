package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IPPPEncoder;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class PPPEncoder implements IPPPEncoder {

	Protocol protocol;
	FrameCheckSequence fcs;

	public PPPEncoder(Protocol protocol, FrameCheckSequence fcs) {
		this.protocol = protocol;
		this.fcs = fcs;
	}

	@Override
	public byte[] encode(ByteArrayInputStream is) {
		return null;
	}

}
