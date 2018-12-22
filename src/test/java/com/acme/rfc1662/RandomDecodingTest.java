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
	private static final int MAX_LENGTH_OF_RANDOM_DATA = 100;
	private static final int MAX_CORRECT_MESSAGES_COUNT = 10;
	private static final int MAX_LENGTH_OF_CORRECT_MESSAGE = 300;

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

					byte[] data = createRandomData(random, random.nextInt(MAX_LENGTH_OF_RANDOM_DATA));
					bos.write(data);

					data = createMessage(random);
					System.out.println("message = " + printer.printAsHex(data, 1));
					
					bos.write(data);

					data = createRandomData(random, random.nextInt(MAX_LENGTH_OF_RANDOM_DATA));
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
	
	byte[] createRandomData(Random random, int length) {
		byte[] data = new byte[length];
		random.nextBytes(data);
		
		for (int i=0;i<data.length;i++) {
			if (data[i] == 0x7E) {
				data[i] = 0x6E;
			}
		}

		return data;
	}

	private byte[] createMessage(Random random) {

		int contentLength = 1 + random.nextInt(MAX_LENGTH_OF_CORRECT_MESSAGE);
		
		System.out.println("Creating message of content length = " + contentLength);
		
		byte[] info = new byte[contentLength];
		random.nextBytes(info);

		System.out.println("Message content was = " + printer.printAsHex(info, 1));
		
		return codec.encode(info);
	}

}
