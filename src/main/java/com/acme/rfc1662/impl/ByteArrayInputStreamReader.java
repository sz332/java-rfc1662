package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.EndOfStreamException;
import com.acme.rfc1662.IByteArrayInputStreamReader;

public class ByteArrayInputStreamReader implements IByteArrayInputStreamReader {

    private static final int END_OF_STREAM = -1;

    @Override
    public int read(final ByteArrayInputStream inputStream) {
        final int data = inputStream.read();

        if (data == END_OF_STREAM) {
            throw new EndOfStreamException();
        }

        return data;
    }

}
