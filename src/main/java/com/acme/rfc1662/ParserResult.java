package com.acme.rfc1662;

import java.util.ArrayList;
import java.util.List;

public class ParserResult {

    private final byte[] remaining;
    private final List<byte[]> messages;

    public ParserResult(final byte[] remaining, final List<byte[]> messages) {
        this.remaining = remaining == null ? null : remaining.clone();
        this.messages = messages;
    }

    public List<byte[]> messages() {
        return messages == null ? new ArrayList<>() : messages;
    }

    public byte[] remaining() {
        return remaining == null ? null : remaining.clone();
    }

}
