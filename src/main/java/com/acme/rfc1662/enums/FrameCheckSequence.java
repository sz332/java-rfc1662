package com.acme.rfc1662.enums;

import com.acme.rfc1662.IFCSCalculator;
import com.acme.rfc1662.impl.FcsCalculator16Bit;
import com.acme.rfc1662.impl.FcsCalculator32Bit;

public enum FrameCheckSequence {

    TWO_OCTET(2, new FcsCalculator16Bit()), FOUR_OCTET(4, new FcsCalculator32Bit());

    IFCSCalculator calculator;
    int lengthInBytes;

    FrameCheckSequence(final int lengthInBytes, final IFCSCalculator calculator) {
        this.lengthInBytes = lengthInBytes;
        this.calculator = calculator;
    }

    public int lengthInBytes() {
        return lengthInBytes;
    }

    public IFCSCalculator calculator() {
        return calculator;
    }
}
