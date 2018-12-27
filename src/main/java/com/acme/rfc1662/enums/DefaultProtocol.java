package com.acme.rfc1662.enums;

public enum DefaultProtocol implements Protocol {

    ONE_OCTET(1, new byte[] { 0x01 }), TWO_OCTET(2, new byte[] { 0x01, 0x02 });

    byte[] identifier;
    int lengthInBytes;

    DefaultProtocol(final int lengthInBytes, final byte[] identifier) {
        this.lengthInBytes = lengthInBytes;
        this.identifier = identifier == null ? null : identifier.clone();
    }

    @Override
    public int lengthInBytes() {
        return lengthInBytes;
    }

    @Override
    public byte[] identifier() {
        return (identifier == null) ? null : identifier.clone();
    }

}
