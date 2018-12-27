package com.acme.rfc1662.impl;

import com.acme.rfc1662.IByteArrayInputStreamReader;
import com.acme.rfc1662.IParsingContextConfig;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class ParsingContextConfig implements IParsingContextConfig {

    private final IByteArrayInputStreamReader reader;
    private final Protocol protocol;
    private final FrameCheckSequence fcs;

    public ParsingContextConfig(final IByteArrayInputStreamReader reader, final Protocol protocol,
            final FrameCheckSequence fcs) {
        this.reader = reader;
        this.protocol = protocol;
        this.fcs = fcs;
    }

    @Override
    public IByteArrayInputStreamReader getReader() {
        return reader;
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public FrameCheckSequence getFcs() {
        return fcs;
    }

}
