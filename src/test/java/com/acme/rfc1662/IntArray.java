package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class IntArray {

	public static ByteArrayInputStream join(int[]... lists) {

		int length = 0;

		for (int i = 0; i < lists.length; i++) {
			length += lists[i].length;
		}

		int[] result = new int[length];

		int position = 0;

		for (int i = 0; i < lists.length; i++) {
			System.arraycopy(lists[i], 0, result, position, lists[i].length);
			position += lists[i].length;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		for (int i : result) {
			bos.write(i);
		}

		return new ByteArrayInputStream(bos.toByteArray());
	}

}
