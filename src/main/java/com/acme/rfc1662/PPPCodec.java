package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.impl.PPPEncoder;
import com.acme.rfc1662.impl.StateMachine;

public class PPPCodec {

    Protocol protocol;
    FrameCheckSequence fcs;

    public PPPCodec(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
    }

    public ParsingResult decode(final ByteArrayInputStream is) {
        final StateMachine sm = new StateMachine(protocol, fcs);
        return sm.parse(is);
    }

    public byte[] encode(final byte[] content) {
        final PPPEncoder encoder = new PPPEncoder(protocol, fcs);
        return encoder.encode(content);
    }

}
