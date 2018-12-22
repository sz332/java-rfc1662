package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;

public class EncoderTest {
	
	PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);
	
	@Test
	public void testEncodeEmpty() {
		
		byte[] data = codec.encode(new byte[0]);
		
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		
		Assert.assertArrayEquals(new byte[] {0x7E, (byte)0xFF, (byte)0x7D, 0x23, 0x01, 0x02, (byte)0xEF, (byte)0x99, (byte)0x7E }, data );
	}
	
	@Test
	public void testEncodeNormalData() {
		byte[] data = codec.encode(new byte[] {0x10, 0x11, 0x12});
		
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		
		Assert.assertArrayEquals(new byte[] {0x7E, (byte)0xFF, (byte)0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x12, (byte)0x83, (byte)0xB5, (byte)0x7E }, data );
	}
	
	@Test
	public void testEncodeFlagInData() {
		byte[] data = codec.encode(new byte[] {0x10, 0x11, 0x7E, 0x12});
		
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		
		Assert.assertArrayEquals(new byte[] {0x7E, (byte)0xFF, (byte)0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x5E,  0x12, (byte)0xED, (byte)0xBB, (byte)0x7E }, data );
	}
	
	@Test
	public void testEncodeEscapeInData() {
		byte[] data = codec.encode(new byte[] {0x10, 0x11, 0x7D, 0x12});
		
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		
		Assert.assertArrayEquals(new byte[] {0x7E, (byte)0xFF, (byte)0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x5D,  0x12, (byte)0xC7, (byte)0xD3, (byte)0x7E }, data );
	}
	
	@Test
	public void testEncodeEtxInData() {
		byte[] data = codec.encode(new byte[] {0x10, 0x11, 0x03, 0x12});
		
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		
		Assert.assertArrayEquals(new byte[] {0x7E, (byte)0xFF, (byte)0x7D, 0x23, 0x01, 0x02, 0x10, 0x11, 0x7D, 0x23,  0x12, (byte)0xAD, (byte)0x07, (byte)0x7E }, data );
	}	


}
