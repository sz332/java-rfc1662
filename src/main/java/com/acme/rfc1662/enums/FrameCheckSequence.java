package com.acme.rfc1662.enums;

import com.acme.rfc1662.IFCSByteArrayCalculator;
import com.acme.rfc1662.impl.Fcs16Calculator;
import com.acme.rfc1662.impl.Fcs32Calculator;

public enum FrameCheckSequence {
	
	TWO_OCTET(2, new Fcs16Calculator()), FOUR_OCTET(4, new Fcs32Calculator());
	
	IFCSByteArrayCalculator calculator;
	int lengthInBytes;
	
	FrameCheckSequence(int lengthInBytes, IFCSByteArrayCalculator calculator){
		this.lengthInBytes = lengthInBytes;
		this.calculator = calculator;
	}
	
	public int lengthInBytes() {
		return lengthInBytes;
	}
	
	public IFCSByteArrayCalculator calculator() {
		return calculator;
	}
}
