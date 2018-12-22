package com.acme.rfc1662.impl;

import com.acme.rfc1662.IFcsCalculator;
import com.acme.rfc1662.IPacketInformation;
import com.acme.rfc1662.IPacketInformationSerializer;

public class FcsCalculator implements IFcsCalculator {

	private static final IPacketInformationSerializer serializer = new PacketInformationSerializer();
	private static final Fcs16Calculator calculator = new Fcs16Calculator();
	
	@Override
	public int calculate(IPacketInformation information) {
		byte[] data = serializer.convertPacketInformation(information);
		return calculator.calculate(data);
	}

}
