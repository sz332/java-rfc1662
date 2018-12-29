package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;

public class PartialDecodeTest {

    private final PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);
    private final IntArray intArray = new IntArray();

    @Test
    public void testPartialHeaderArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(1, result.remaining().length);
    }

    @Test
    public void testPartialAddressArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(2, result.remaining().length);
    }

    @Test
    public void testPartialControlArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(4, result.remaining().length);
    }

    @Test
    public void testPartialProtocolPartialArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(5, result.remaining().length);
    }

    @Test
    public void testPartialProtocolArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(6, result.remaining().length);
    }

    @Test
    public void testPartialContentArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(7, result.remaining().length);
    }

    @Test
    public void testMoreContentArrived() {
        final ParsingResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(8, result.remaining().length);
    }

    @Test
    public void testPartialCRCArrived() {
        final ParsingResult result = codec
                .decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(9, result.remaining().length);
    }

    @Test
    public void testCRCArrived() {
        final ParsingResult result = codec
                .decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d, 0x4f }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(10, result.remaining().length);
    }

    @Test
    public void testCRCArrivedButWrong() {
        final ParsingResult result = codec
                .decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x0d, 0x4f, 0x7E }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testFullMessageArrived() {
        final ParsingResult result = codec
                .decode(intArray.join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d, 0x4f, 0x7E }));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);

        Assert.assertArrayEquals(result.messages().get(0), new byte[] { 0x01, 0x02 });
    }

}
