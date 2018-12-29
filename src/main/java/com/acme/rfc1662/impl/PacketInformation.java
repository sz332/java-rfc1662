package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.acme.rfc1662.IPacketInformation;

public class PacketInformation implements IPacketInformation {

    byte @NonNull [] protocol;
    byte @NonNull [] information;

    @NonNull
    ByteArrayInputStream message;

    public PacketInformation() {
        this.protocol = new byte[0];
        this.information = new byte[0];
        this.message = new ByteArrayInputStream(new byte[0]);
    }

    @Override
    public byte @NonNull [] getProtocol() {
        return protocol.clone();
    }

    @Override
    public void setProtocol(final byte @NonNull [] protocol) {
        this.protocol = protocol.clone();
    }

    @Override
    public byte @NonNull [] getInformation() {
        return information.clone();
    }

    @Override
    public void setInformation(final byte @NonNull [] information) {
        this.information = information.clone();
    }

    @Override
    public @NonNull ByteArrayInputStream getMessageAsStream() {
        return message;
    }

    @Override
    public void setMessageAsStream(@NonNull final ByteArrayInputStream message) {
        this.message = message;
    }

}
