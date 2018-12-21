package com.acme.rfc1662;

public interface IParseStateMachine {

	enum State {
		MatchAddressFieldState,
		MatchControlFieldState,
		MatchProtocolFieldState,
		ParseValidMessageState,
		ReadUntilEndingFlagState,
		ReadUntilFirstMatchingFlagState,
		SeparateInformationFromChecksumState,
		UnknownProtocolLengthState
	}
	
	void setState(State state);

}
