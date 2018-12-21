package com.acme.rfc1662;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.impl.EscapeDecoder;

public class EscapeDecoderTest {

	IEscapeDecoder decoder = new EscapeDecoder();

	@Test(expected = EndOfStreamException.class)
	public void testEmptyArray() {
		decoder.read(new ByteArrayInputStream(new byte[0]));
	}

	@Test
	public void testNormalData() {
		int value = decoder.read(new ByteArrayInputStream(new byte[] { 0x05 }));
		Assert.assertEquals(5, value);
	}

	@Test(expected = EndOfStreamException.class)
	public void testArrayWithControlEscapeNoData() {
		decoder.read(new ByteArrayInputStream(new byte[] { 0x7d }));
	}

	@Test
	public void testArrayWithControlEscapeAndData() {
		int value = decoder.read(new ByteArrayInputStream(new byte[] { 0x7d, 0x21 }));
		Assert.assertEquals(1, value);
	}

}
