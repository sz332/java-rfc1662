package com.acme.rfc1662.impl;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class ParsingContextConfig implements IParsingContextConfig {

	private final IEscapeDecoder decoder;
	private final Protocol protocol;
	private final FrameCheckSequence fcs;

	public ParsingContextConfig(IEscapeDecoder decoder, Protocol protocol, FrameCheckSequence fcs) {
		this.decoder = decoder;
		this.protocol = protocol;
		this.fcs = fcs;
	}

	@Override
	public IEscapeDecoder getDecoder() {
		return decoder;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public FrameCheckSequence getFcs() {
		return fcs;
	}

}
