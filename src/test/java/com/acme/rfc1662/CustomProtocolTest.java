package com.acme.rfc1662;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class CustomProtocolTest {

    enum CustomProtocol implements Protocol {

        DEFAULT(2, new byte[] { 0x01, 0x02 });

        int lengthInBytes;
        byte[] identifier;

        CustomProtocol(final int lengthInBytes, final byte[] identifier) {
            this.lengthInBytes = lengthInBytes;
            this.identifier = identifier == null ? null : identifier.clone();
        }

        @Override
        public int lengthInBytes() {
            return lengthInBytes;
        }

        @Override
        public byte[] identifier() {
            return identifier == null ? null : identifier.clone();
        }

    }

    @Test
    public void testEncode() {

        final PPPCodec codec = new PPPCodec(CustomProtocol.DEFAULT, FrameCheckSequence.TWO_OCTET);

        final byte[] data = codec.encode(new byte[] { 0x01, 0x02, 0x03 });

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length > 0);

        Assert.assertEquals(data[4], CustomProtocol.DEFAULT.identifier[0]);
        Assert.assertEquals(data[5], CustomProtocol.DEFAULT.identifier[1]);
    }

}
