package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

public class PPPParser {

	public ParserResult parse(ByteArrayInputStream is) {
		PPPParserStateMachine sm = new PPPParserStateMachine();
		return sm.parse(is);
	}

}
