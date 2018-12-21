package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.IFcsCalculator;
import com.acme.rfc1662.IParsingContext;

public class ParseContext implements IParsingContext {

	private final IEscapeDecoder decoder;
	private final IFcsCalculator calculator;
	private final ByteArrayInputStream inputStream;
	private final int protocolFieldLengthInBytes;
	private final int fcsLengthInBytes;
	private final List<byte[]> messages;

	public ParseContext(IEscapeDecoder decoder, IFcsCalculator calculator, ByteArrayInputStream inputStream, int protocolFieldLengthInBytes, int fcsLengthInBytes) {
		this.decoder = decoder;
		this.calculator = calculator;
		this.inputStream = inputStream;
		this.protocolFieldLengthInBytes = protocolFieldLengthInBytes;
		this.fcsLengthInBytes = fcsLengthInBytes;
		this.messages = new ArrayList<>();
	}

	@Override
	public IEscapeDecoder getDecoder() {
		return decoder;
	}
	
	@Override
	public IFcsCalculator getFcsCalculator() {
		return calculator;
	}
	
	@Override
	public ByteArrayInputStream getInputStream() {
		return this.inputStream;
	}

	@Override
	public int protocolFieldLengthInBytes() {
		return this.protocolFieldLengthInBytes;
	}

	@Override
	public int fcsLengthInBytes() {
		return this.fcsLengthInBytes;
	}
	
	@Override
	public void addMessage(byte[] data) {
		this.messages.add(data);
	}

	@Override
	public List<byte[]> getMessages() {
		return this.messages;
	}
	
}
