package com.acme.rfc1662;

import static com.acme.rfc1662.IntArray.join;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;


// http://www.netfor2.com/ppp.htm
// https://stackoverflow.com/questions/7983862/calculating-fcscrc-for-hdlc-frame
public class DecodeTest {

	private static final int[] MESSAGE_1 = new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x7D, 0x21, 0x7D, 0x22, 0x7D, 0x20, 0x7D, 0x38,
			0x7D, 0x21, 0x7D, 0x24, 0x7D, 0x25, 0xDC, 0x7D, 0x22, 0x7D, 0x26, 0x7D, 0x20, 0x7D, 0x20, 0x7D, 0x20, 0x7D, 0x20, 0x7D, 0x25,
			0x7D, 0x26, 0x29, 0x23, 0xBE, 0x84, 0x7D, 0x27, 0x7D, 0x22, 0x7D, 0x28, 0x7D, 0x22, 0xDF, 0x7D, 0x30, 0x7E };

	
	PPPCodec codec = new PPPCodec(Protocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);
	
	@Test
	public void testEmptyStream() {
		ParserResult result = codec.decode(join(new int[0]));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testStandardMessage() {
		ParserResult result = codec.decode(join(MESSAGE_1));

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testTwoCorrectMessages() {

		ParserResult result = codec.decode(join(MESSAGE_1, MESSAGE_1));

		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testTrashBeforeMessage() {
		ParserResult result = codec.decode(join(new int[] { 0x10, 0x11, 0x12 }, MESSAGE_1));

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	// FIXME
	@Test
	public void testTrashAfterMessage() {
		ParserResult result = codec.decode(join(MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	// FIXME
	@Test
	public void testTrashBeforeAfterMessage() {
		ParserResult result = codec.decode(join(new int[] { 0x04, 0x05, 0x06 }, MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testTrashBetweenMessages() {
		ParserResult result = codec.decode(join(MESSAGE_1, new int[] { 0x10, 0x11, 0x12 }, MESSAGE_1));

		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testWrongAddress() {
		ParserResult result = codec.decode(join(new int[] { 0x7E, 0x12, 0x03 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testWrongControl() {
		ParserResult result = codec.decode(join(new int[] { 0x7E, 0xFF, 0x04 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testNoContentNoCRCButClosing() {
		ParserResult result = codec.decode(join(new int[] { 0x7E, 0xFF, 0x03, 0x15, 0x13, 0x7E }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	@Test
	public void testNoContentNoCRCAndClosing() {
		ParserResult result = codec.decode(join(new int[] { 0x7E, 0xFF, 0x03, 0x15, 0x00, 0x00, 0x7E }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.messages().size());
		Assert.assertEquals(0, result.remaining().length);
	}

	

}
