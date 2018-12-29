package com.acme.rfc1662.impl;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.acme.rfc1662.IOutputContext;

public class OutputContext implements IOutputContext {

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
    public void setRemaining(@NonNull final byte[] remaining) {
        this.remaining = remaining.clone();
    }

    @Override
    public byte[] getRemaining() {
        return remaining;
    }

}
