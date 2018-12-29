package com.acme.rfc1662;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public interface IContextConfig {

    IByteArrayInputStreamReader getReader();

    Protocol getProtocol();

    FrameCheckSequence getFcs();

}
