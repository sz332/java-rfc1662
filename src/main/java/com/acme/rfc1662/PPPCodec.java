package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.impl.PPPEncoder;
import com.acme.rfc1662.impl.StateMachine;

public class PPPCodec {

    private final Protocol protocol;
    private final FrameCheckSequence fcs;
    private final StateMachine sm;

    public PPPCodec(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
        this.sm = new StateMachine(protocol, fcs);
    }

    public ParsingResult decode(final ByteArrayInputStream is) {
        return sm.parse(is);
    }

    public byte[] encode(final byte[] content) {
        final PPPEncoder encoder = new PPPEncoder(protocol, fcs);
        return encoder.encode(content);
    }

}
