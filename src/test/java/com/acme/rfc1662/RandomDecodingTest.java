package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.acme.rfc1662.enums.DefaultProtocol;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.util.ByteArrayPrinter;

public class RandomDecodingTest {

	private static final int ITERATIONS = 100;
	private static final int MAX_LENGTH_OF_RANDOM_DATA = 200;
	private static final int MAX_CORRECT_MESSAGES_COUNT = 5;
	private static final int MAX_LENGTH_OF_CORRECT_MESSAGE = 5;

	ByteArrayPrinter printer = new ByteArrayPrinter();
	PPPCodec codec = new PPPCodec(DefaultProtocol.TWO_OCTET, FrameCheckSequence.TWO_OCTET);

	@Test
	public void testDecodingWithRandomData() {

		Random random = new Random();

		for (int i = 0; i < ITERATIONS; i++) {
			
			System.out.println("Working on iteration " + i);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			try {

				int correctMessageCount = random.nextInt(MAX_CORRECT_MESSAGES_COUNT);

				System.out.println("Working on iteration " + i);

				
				for (int j = 0; j < correctMessageCount; j++) {
					byte[] data = new byte[random.nextInt(MAX_LENGTH_OF_RANDOM_DATA)];
					random.nextBytes(data);

					bos.write(data);

					data = createMessage(random);
					System.out.println("message = " + printer.printAsHex(data, 1));
					
					bos.write(data);

					data = new byte[random.nextInt(MAX_LENGTH_OF_RANDOM_DATA)];
					random.nextBytes(data);

					bos.write(data);
				}

				byte[] dataToDecode = bos.toByteArray();
				
				System.out.println("stream = " + printer.printAsHex(dataToDecode, 1));
				
				ParserResult result = codec.decode(new ByteArrayInputStream(dataToDecode));

				Assert.assertEquals(correctMessageCount, result.messages().size());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] createMessage(Random random) {

		int contentLength = random.nextInt(MAX_LENGTH_OF_CORRECT_MESSAGE);
		
		System.out.println("Creating message of content length = " + contentLength);
		
		byte[] info = new byte[contentLength];
		random.nextBytes(info);

		return codec.encode(info);
	}

}
