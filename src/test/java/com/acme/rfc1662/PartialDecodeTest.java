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

}
