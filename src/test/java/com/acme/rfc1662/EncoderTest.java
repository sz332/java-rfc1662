package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;

public class EncoderTest {

    PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);

    @Test
    public void testEncodeEmpty() {
        final byte[] data = codec.encode(new byte[0]);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertArrayEquals(
                new byte[] { 0x7E, (byte) 0xFF, (byte) 0x7D, 0x23, 0x01, 0x02, (byte) 0xA2, (byte) 0xEC, (byte) 0x7E },
                data);
    }

    @Test
    public void testEncodeNormalData() {
        final byte[] data = codec.encode(new byte[] { 0x10, 0x11, 0x12 });

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertArrayEquals(new byte[] { 0x7E, (byte) 0xFF, (byte) 0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x12,
                (byte) 0xD5, (byte) 0x06, (byte) 0x7E }, data);
    }

    @Test
    public void testEncodeFlagInData() {
        final byte[] data = codec.encode(new byte[] { 0x10, 0x11, 0x7E, 0x12 });

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertArrayEquals(new byte[] { 0x7E, (byte) 0xFF, (byte) 0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x5E,
                0x12, (byte) 0x38, (byte) 0x8E, (byte) 0x7E }, data);
    }

    @Test
    public void testEncodeEscapeInData() {
        final byte[] data = codec.encode(new byte[] { 0x10, 0x11, 0x7D, 0x12 });

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertArrayEquals(new byte[] { 0x7E, (byte) 0xFF, (byte) 0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x5D,
                0x12, (byte) 0x50, (byte) 0xA4, (byte) 0x7E }, data);
    }

    @Test
    public void testEncodeEtxInData() {
        final byte[] data = codec.encode(new byte[] { 0x10, 0x11, 0x03, 0x12 });

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertArrayEquals(new byte[] { 0x7E, (byte) 0xFF, (byte) 0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x23,
                0x12, (byte) 0x84, (byte) 0xCE, (byte) 0x7E }, data);
    }

}
