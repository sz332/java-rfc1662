package com.acme.rfc1662.util;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayPrinterTest {

    final ByteArrayPrinter printer = new ByteArrayPrinter();

    @Test
    public void testPrinterSingleByte() {
        Assert.assertEquals("20", printer.printAsHex(new byte[] { 0x20 }, 1));
    }

    @Test
    public void testPrinterTwoBytes() {
        Assert.assertEquals("20 3A", printer.printAsHex(new byte[] { 0x20, 0x3a }, 1));
    }

}
