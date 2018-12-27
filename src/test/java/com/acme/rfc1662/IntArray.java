package com.acme.rfc1662;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class IntArray {

    public ByteArrayInputStream join(final int[]... lists) {

        int length = 0;

        for (final int[] list : lists) {
            length += list.length;
        }

        final int[] result = new int[length];

        int position = 0;

        for (final int[] list : lists) {
            System.arraycopy(list, 0, result, position, list.length);
            position += list.length;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        for (final int i : result) {
            bos.write(i);
        }

        return new ByteArrayInputStream(bos.toByteArray());
    }

}
