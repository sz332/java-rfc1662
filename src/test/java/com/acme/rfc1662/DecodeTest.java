package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;

// http://www.netfor2.com/ppp.htm
// https://stackoverflow.com/questions/7983862/calculating-fcscrc-for-hdlc-frame
public class DecodeTest {

    private static final int[] MESSAGE_1 = new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x7D, 0x21, 0x7D, 0x22, 0x7D,
            0x20, 0x7D, 0x38, 0x7D, 0x21, 0x7D, 0x24, 0x7D, 0x25, 0xDC, 0x7D, 0x22, 0x7D, 0x26, 0x7D, 0x20, 0x7D, 0x20,
            0x7D, 0x20, 0x7D, 0x20, 0x7D, 0x25, 0x7D, 0x26, 0x29, 0x23, 0xBE, 0x84, 0x7D, 0x27, 0x7D, 0x22, 0x7D, 0x28,
            0x7D, 0x22, 0xDF, 0x7D, 0x30, 0x7E };

    private final PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);
    
    private final IntArray intArray = new IntArray();

    @Test
    public void testEmptyStream() {
        final ParserResult result = codec.decode(intArray.join(new int[0]));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testStandardMessage() {
        final ParserResult result = codec.decode(intArray.join(MESSAGE_1));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testTwoCorrectMessages() {

        final ParserResult result = codec.decode(intArray.join(MESSAGE_1, MESSAGE_1));

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testTrashBeforeMessage() {
        final ParserResult result = codec.decode(intArray.join(new int[] { 0x10, 0x11, 0x12 }, MESSAGE_1));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    // FIXME
    @Test
    public void testTrashAfterMessage() {
        final ParserResult result = codec.decode(intArray.join(MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testTrashBeforeAfterMessage() {
        final ParserResult result = codec
                .decode(intArray.join(new int[] { 0x04, 0x05, 0x06 }, MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testTrashBetweenMessages() {
        final ParserResult result = codec.decode(intArray.join(MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }, MESSAGE_1));

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testWrongAddress() {
        final ParserResult result = codec.decode(intArray.join(new int[] { 0x7E, 0x12, 0x03 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(3, result.remaining().length);
    }

    @Test
    public void testWrongControl() {
        final ParserResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x04 }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(3, result.remaining().length);
    }

    @Test
    public void testNoContentNoCRCButClosing() {
        final ParserResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x03, 0x15, 0x13, 0x7E }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

    @Test
    public void testNoContentNoCRCAndClosing() {
        final ParserResult result = codec.decode(intArray.join(new int[] { 0x7E, 0xFF, 0x03, 0x15, 0x00, 0x00, 0x7E }));

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.messages().size());
        Assert.assertEquals(0, result.remaining().length);
    }

}
