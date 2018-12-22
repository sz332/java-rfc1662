package com.acme.rfc1662;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public interface IParsingContextConfig {

	IEscapeDecoder getDecoder();

	Protocol getProtocol();
	
	FrameCheckSequence getFcs();


}
