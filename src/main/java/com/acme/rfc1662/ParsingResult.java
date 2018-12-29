package com.acme.rfc1662;

import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ParsingResult {

    private final byte[] remaining;
    private final List<byte[]> messages;

    public ParsingResult(@NonNull final byte[] remaining, @NonNull final List<byte[]> messages) {
        this.remaining = remaining.clone();
        this.messages = messages;
    }

    public List<byte[]> messages() {
        return messages;
    }

    public byte[] remaining() {
        return remaining.clone();
    }
}
