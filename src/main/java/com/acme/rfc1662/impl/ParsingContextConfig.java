package com.acme.rfc1662.impl;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.IFcsCalculator;
import com.acme.rfc1662.IParsingContextConfig;

public class ParsingContextConfig implements IParsingContextConfig {

	private final IEscapeDecoder decoder;
	private final IFcsCalculator calculator;
	private final int protocolFieldLengthInBytes;
	private final int fcsLengthInBytes;

	public ParsingContextConfig(IEscapeDecoder decoder, IFcsCalculator calculator, int protocolFieldLengthInBytes, int fcsLengthInBytes) {
		this.decoder = decoder;
		this.calculator = calculator;
		this.protocolFieldLengthInBytes = protocolFieldLengthInBytes;
		this.fcsLengthInBytes = fcsLengthInBytes;
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
	public int protocolFieldLengthInBytes() {
		return protocolFieldLengthInBytes;
	}

	@Override
	public int fcsLengthInBytes() {
		return fcsLengthInBytes;
	}

}
