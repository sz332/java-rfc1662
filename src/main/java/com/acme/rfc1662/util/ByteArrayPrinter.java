package com.acme.rfc1662.util;

public class ByteArrayPrinter {

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private static final char BUNDLE_SEP = ' ';

	public String printAsHex(byte[] bytes, int bundleSize) {
		char[] hexChars = new char[(bytes.length * 2) + (bytes.length / bundleSize)];
		for (int j = 0, k = 1; j < bytes.length; j++, k++) {
			int v = bytes[j] & 0xFF;
			int start = (j * 2) + j / bundleSize;

			hexChars[start] = HEX_ARRAY[v >>> 4];
			hexChars[start + 1] = HEX_ARRAY[v & 0x0F];

			if ((k % bundleSize) == 0) {
				hexChars[start + 2] = BUNDLE_SEP;
			}
		}
		return new String(hexChars).trim();
	}

}
