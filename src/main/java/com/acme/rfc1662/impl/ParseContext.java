package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.IFcsCalculator;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextResult;

public class ParseContext implements IParsingContext {

	private final IEscapeDecoder decoder;
	private final IFcsCalculator calculator;
	private final ByteArrayInputStream inputStream;
	private final int protocolFieldLengthInBytes;
	private final int fcsLengthInBytes;
	private final IParsingContextResult result;
	
	public ParseContext(IEscapeDecoder decoder, IFcsCalculator calculator, ByteArrayInputStream inputStream, int protocolFieldLengthInBytes, int fcsLengthInBytes) {
		this.decoder = decoder;
		this.calculator = calculator;
		this.inputStream = inputStream;
		this.protocolFieldLengthInBytes = protocolFieldLengthInBytes;
		this.fcsLengthInBytes = fcsLengthInBytes;
		this.result = new ParsingContextResult();
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
	public IParsingContextResult result() {
		return result;
	}
}
