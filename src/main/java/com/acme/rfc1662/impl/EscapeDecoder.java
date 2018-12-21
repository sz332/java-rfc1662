package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.EndOfStreamException;

public class EscapeDecoder implements IEscapeDecoder {

	private static final int END_OF_STREAM = -1;
	private static final int CONTROL_ESCAPE = 0x7d;

	@Override
	public int read(ByteArrayInputStream inputStream) {

		int data = inputStream.read();

		if (data == END_OF_STREAM) {
			throw new EndOfStreamException();
		} else if (data == CONTROL_ESCAPE) {
			
			data = inputStream.read();
			
			if (data == END_OF_STREAM) {
				throw new EndOfStreamException();
			} else {
				return data ^ 0x20;
			}
			
		} else {
			return data;
		}
	}

}
