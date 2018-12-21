package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IPacketInformation;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.IParsingContextResult;

public class ParsingContext implements IParsingContext {

	private final ByteArrayInputStream inputStream;
	private final IParsingContextConfig config;
	private final IParsingContextResult result;
	private final IPacketInformation packetInformation;
	
	public ParsingContext(IParsingContextConfig config, ByteArrayInputStream inputStream) {
		this.config = config;
		this.inputStream = inputStream;
		this.result = new ParsingContextResult();
		this.packetInformation = new PacketInformation();
	}

	@Override
	public ByteArrayInputStream getInputStream() {
		return this.inputStream;
	}

	@Override
	public IParsingContextConfig config() {
		return config;
	}

	@Override
	public IParsingContextResult result() {
		return result;
	}
	
	@Override
	public IPacketInformation packetInformation() {
		return packetInformation;
	}
}
