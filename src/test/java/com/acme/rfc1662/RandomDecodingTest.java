package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.util.ByteArrayPrinter;

public class RandomDecodingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDecodingTest.class);

    private static final int ITERATIONS = 100;
    private static final int MAX_LENGTH_OF_RANDOM_DATA = 100;
    private static final int MAX_CORRECT_MESSAGES_COUNT = 10;
    private static final int MAX_LENGTH_OF_CORRECT_MESSAGE = 300;

    ByteArrayPrinter printer = new ByteArrayPrinter();
    PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);

    @Test
    public void testDecodingWithRandomData() {

        final Random random = new Random();

        for (int i = 0; i < ITERATIONS; i++) {

            LOGGER.info("Working on iteration {}", i);

            final ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {

                final int correctMessageCount = random.nextInt(MAX_CORRECT_MESSAGES_COUNT);

                for (int j = 0; j < correctMessageCount; j++) {

                    byte[] data = createRandomData(random, random.nextInt(MAX_LENGTH_OF_RANDOM_DATA));
                    bos.write(data);

                    data = createMessage(random);
                    LOGGER.info("message = {}", printer.printAsHex(data, 1));

                    bos.write(data);

                    data = createRandomData(random, random.nextInt(MAX_LENGTH_OF_RANDOM_DATA));
                    bos.write(data);
                }

                final byte[] dataToDecode = bos.toByteArray();

                LOGGER.info("stream = {}", printer.printAsHex(dataToDecode, 1));

                final ParsingResult result = codec.decode(new ByteArrayInputStream(dataToDecode));

                Assert.assertEquals(correctMessageCount, result.messages().size());

            } catch (final IOException e) {
                LOGGER.error("IOException occured", e);
            }
        }
    }

    byte[] createRandomData(final Random random, final int length) {
        final byte[] data = new byte[length];
        random.nextBytes(data);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0x7E) {
                data[i] = 0x6E;
            }
        }

        return data;
    }

    private byte[] createMessage(final Random random) {

        final int contentLength = 1 + random.nextInt(MAX_LENGTH_OF_CORRECT_MESSAGE);

        LOGGER.info("Creating message of content length = {}", contentLength);

        final byte[] info = new byte[contentLength];
        random.nextBytes(info);

        LOGGER.info("Message content was = {}", printer.printAsHex(info, 1));

        return codec.encode(info);
    }

}
