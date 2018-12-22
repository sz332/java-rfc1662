package com.acme.rfc1662;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import org.junit.Assert;
import org.junit.Test;

public class CustomProtocolTest {

	enum CustomProtocol implements Protocol {

		DEFAULT(2, new byte[] { 0x01, 0x02 });

		int lengthInBytes;
		byte[] identifier;

		CustomProtocol(int lengthInBytes, byte[] identifier) {
			this.lengthInBytes = lengthInBytes;
			this.identifier = identifier;
		}

		@Override
		public int lengthInBytes() {
			return lengthInBytes;
		}

		@Override
		public byte[] identifier() {
			return identifier;
		}

	}

	@Test
	public void testEncode() {

		PPPCodec codec = new PPPCodec(CustomProtocol.DEFAULT, FrameCheckSequence.TWO_OCTET);

		byte[] data = codec.encode(new byte[] { 0x01, 0x02, 0x03 });

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);

		Assert.assertEquals(data[4], CustomProtocol.DEFAULT.identifier[0]);
		Assert.assertEquals(data[5], CustomProtocol.DEFAULT.identifier[1]);
	}

}
