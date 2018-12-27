package com.acme.rfc1662.impl;

import java.util.ArrayList;
import java.util.List;

import com.acme.rfc1662.IParsingContextResult;

public class ParsingContextResult implements IParsingContextResult {

    private final List<byte[]> messages = new ArrayList<>();
    private byte[] remaining = new byte[0];

    @Override
    public void addMessage(final byte[] data) {
        messages.add(data);
    }

    @Override
    public List<byte[]> getMessages() {
        return messages;
    }

    @Override
    public void setRemaining(final byte[] remaining) {
        this.remaining = remaining == null ? null : remaining.clone();
    }

    @Override
    public byte[] getRemaining() {
        return remaining;
    }

}
