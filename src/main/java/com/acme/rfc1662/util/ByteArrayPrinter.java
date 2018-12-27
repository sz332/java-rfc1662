package com.acme.rfc1662.util;

public class ByteArrayPrinter {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final char BUNDLE_SEP = ' ';

    public String printAsHex(final byte[] bytes, final int bundleSize) {
        final char[] hexChars = new char[bytes.length * 2 + bytes.length / bundleSize];
        
        int k = 1;
        
        for (int j = 0;j < bytes.length; j++) {
            final int v = bytes[j] & 0xFF;
            final int start = j * 2 + j / bundleSize;

            hexChars[start] = ByteArrayPrinter.HEX_ARRAY[v >>> 4];
            hexChars[start + 1] = ByteArrayPrinter.HEX_ARRAY[v & 0x0F];

            if (k % bundleSize == 0) {
                hexChars[start + 2] = ByteArrayPrinter.BUNDLE_SEP;
            }
            
            k++;
        }
        return new String(hexChars).trim();
    }

}
