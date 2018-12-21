package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;
import static com.acme.rfc1662.IntArray.join;

public class PartialDecodeTest {

	@Test
	public void testPartialHeaderArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(1, result.getRemaining().length);
	}

	@Test
	public void testPartialAddressArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(2, result.getRemaining().length);
	}

	@Test
	public void testPartialControlArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(4, result.getRemaining().length);
	}

	@Test
	public void testPartialProtocolPartialArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(5, result.getRemaining().length);
	}

	@Test
	public void testPartialProtocolArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(6, result.getRemaining().length);
	}

	@Test
	public void testPartialContentArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(7, result.getRemaining().length);
	}

	@Test
	public void testMoreContentArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02 }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(8, result.getRemaining().length);
	}

	@Test
	public void testPartialCRCArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(9, result.getRemaining().length);
	}

	@Test
	public void testCRCArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d, 0x4f }));

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getMessages().size());
		Assert.assertEquals(10, result.getRemaining().length);
	}

	@Test
	public void testFullMessageArrived() {
		ParserResult result = new PPPParser().parse(join(new int[] { 0x7E, 0xFF, 0x7D, 0x23, 0xC0, 0x21, 0x01, 0x02, 0x9d, 0x4f, 0x7E }));

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.getMessages().size());
		Assert.assertEquals(0, result.getRemaining().length);

		Assert.assertArrayEquals(result.getMessages().get(0), new byte[] { 0x01, 0x02 });
	}

}
