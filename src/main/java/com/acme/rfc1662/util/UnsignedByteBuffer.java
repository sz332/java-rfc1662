package com.acme.rfc1662.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UnsignedByteBuffer {

	private final ByteBuffer byteBuffer;

	public UnsignedByteBuffer(int capacity) {
		byteBuffer = ByteBuffer.allocate(capacity);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
	}

	public int intValue() {
		byteBuffer.flip();
	    return byteBuffer.getInt();
	}

	public void putUnsignedByte(int value) {
		byteBuffer.put((byte) (value & 0xff));
	}

	public void putUnsignedByte(int position, int value) {
		byteBuffer.put(position, (byte) (value & 0xff));
	}

}
