package com.acme.rfc1662.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.acme.rfc1662.IPPPEncoder;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class PPPEncoder implements IPPPEncoder {

    private static final int ETX = 0x03;
    private static final int FIELD_FLAG = 0x7E;
    private static final int FIELD_ADDRESS = 0xFF;
    private static final int CONTROL_ESCAPE = 0x7D;
    private static final int FIELD_CONTROL = 0x03;
    private static final int XOR_VALUE = 0x20;

    Protocol protocol;
    FrameCheckSequence fcs;

    public PPPEncoder(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
    }

    @Override
    public byte[] encode(final byte[] content) {

        final int crc = fcs.calculator().calculate(
                calculateFullMessage(FIELD_ADDRESS, FIELD_CONTROL, protocol.identifier(), content, new byte[0]));
        final byte[] crcArray = convertCrc(crc, fcs.lengthInBytes());

        final byte[] escaped = escape(
                calculateFullMessage(FIELD_ADDRESS, FIELD_CONTROL, protocol.identifier(), content, crcArray));

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(FIELD_FLAG);
        bos.write(escaped, 0, escaped.length);
        bos.write(FIELD_FLAG);

        return bos.toByteArray();
    }

    private byte[] calculateFullMessage(final int fieldAddress, final int fieldControl, final byte[] protocol,
            final byte[] content, final byte[] crcArray) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(fieldAddress);
        bos.write(fieldControl);
        bos.write(protocol, 0, protocol.length);
        bos.write(content, 0, content.length);
        bos.write(crcArray, 0, crcArray.length);

        return bos.toByteArray();
    }

    private byte[] convertCrc(final int crc, final int lengthInBytes) {

        byte[] result;
        
        if (lengthInBytes == 2) {
            result = ByteBuffer.allocate(lengthInBytes).order(ByteOrder.LITTLE_ENDIAN).putShort((short) crc).array();
        } else {
            result = ByteBuffer.allocate(lengthInBytes).order(ByteOrder.LITTLE_ENDIAN).putInt(crc).array();
        }
        
        return result;
    }

    private byte[] escape(final byte[] data) {

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        for (final byte b : data) {
            if (b == FIELD_FLAG || b == CONTROL_ESCAPE || b == ETX) {
                bos.write(CONTROL_ESCAPE);
                bos.write(b ^ XOR_VALUE);
            } else {
                bos.write(b);
            }
        }

        return bos.toByteArray();
    }

}
