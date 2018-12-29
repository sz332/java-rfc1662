package com.acme.rfc1662.enums;

import org.checkerframework.checker.nullness.qual.NonNull;

public enum DefaultProtocol implements Protocol {

    ONE_OCTET(1, new byte[] { 0x01 }), TWO_OCTET(2, new byte[] { 0x01, 0x02 });

    byte[] identifier;
    int lengthInBytes;

    DefaultProtocol(final int lengthInBytes,@NonNull final byte[] identifier) {
        this.lengthInBytes = lengthInBytes;
        this.identifier = identifier.clone();
    }

    @Override
    public int lengthInBytes() {
        return lengthInBytes;
    }

    @Override
    public byte[] identifier() {
        return identifier.clone();
    }

}
