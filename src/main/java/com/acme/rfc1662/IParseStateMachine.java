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
		UnknownProtocolLengthState,
		EndOfStreamState
	}
	
	void setState(State state);

}
