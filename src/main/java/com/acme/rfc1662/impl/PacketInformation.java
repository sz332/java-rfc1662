package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;

import com.acme.rfc1662.IPacketInformation;

public class PacketInformation implements IPacketInformation {

    byte[] protocol;
    byte[] information;

    ByteArrayInputStream message;

    @Override
    public byte[] getProtocol() {
        return protocol == null ? null : protocol.clone();
    }

    @Override
    public void setProtocol(final byte[] protocol) {
        this.protocol = protocol.clone();
    }

    @Override
    public byte[] getInformation() {
        return information == null ? null : information.clone();
    }

    @Override
    public void setInformation(final byte[] information) {
        this.information = information.clone();
    }

    @Override
    public ByteArrayInputStream getMessageAsStream() {
        return message;
    }

    @Override
    public void setMessageAsStream(final ByteArrayInputStream message) {
        this.message = message;
    }

}
