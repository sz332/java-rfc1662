package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class IntArray {

	public static ByteArrayInputStream join(int[]... lists) {

		int length = 0;

		for (int[] list : lists) {
			length += list.length;
		}

		int[] result = new int[length];

		int position = 0;

		for (int[] list : lists) {
			System.arraycopy(list, 0, result, position, list.length);
			position += list.length;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		for (int i : result) {
			bos.write(i);
		}

		return new ByteArrayInputStream(bos.toByteArray());
	}

}
