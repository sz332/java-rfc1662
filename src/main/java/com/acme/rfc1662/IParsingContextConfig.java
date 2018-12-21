package com.acme.rfc1662;

public interface IParsingContextConfig {

	IEscapeDecoder getDecoder();

	IFcsCalculator getFcsCalculator();

	int protocolFieldLengthInBytes();

	int fcsLengthInBytes();

}
