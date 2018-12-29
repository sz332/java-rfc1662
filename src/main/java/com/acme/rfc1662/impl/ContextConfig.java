package com.acme.rfc1662.impl;

import com.acme.rfc1662.IByteArrayInputStreamReader;
import com.acme.rfc1662.IContextConfig;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class ContextConfig implements IContextConfig {

    private final IByteArrayInputStreamReader reader;
    private final Protocol protocol;
    private final FrameCheckSequence fcs;

    public ContextConfig(final IByteArrayInputStreamReader reader, final Protocol protocol, final FrameCheckSequence fcs) {
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
