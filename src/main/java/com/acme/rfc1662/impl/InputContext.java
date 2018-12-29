package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IContextConfig;

public class InputContext implements IInputContext {

    private final ByteArrayInputStream inputStream;
    private final IContextConfig config;

    public InputContext(@NonNull final IContextConfig config, @NonNull final ByteArrayInputStream inputStream) {
        this.config = config;
        this.inputStream = inputStream;
    }

    @Override
    public ByteArrayInputStream inputStream() {
        return this.inputStream;
    }

    @Override
    public IContextConfig config() {
        return config;
    }

}
