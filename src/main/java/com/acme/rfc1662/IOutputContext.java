package com.acme.rfc1662;

import java.util.List;

public interface IOutputContext {

    void addMessage(byte[] data);

    List<byte[]> getMessages();

    void setRemaining(byte[] data);

    byte[] getRemaining();
}
