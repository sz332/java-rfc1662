package com.acme.rfc1662;

public interface IParsingStateMachine {

	enum State {
		MATCH_ADDRESS_FIELD_STATE,
		MATCH_CONTROL_FIELD_STATE,
		MATCH_PROTOCOL_FIELD_STATE,
		PARSE_VALID_MESSAGE_STATE,
		READ_UNTIL_END_FLAG_STATE,
		READ_UNTIL_FIRST_MATCHING_FLAG_STATE,
		SEPARATE_INFORMATION_FROM_CHECKSUM_STATE,
		UNKNOWN_PROTOCOL_LENGTH_STATE,
		END_OF_STREAM_STATE
	}
	
	void setState(State state);

}