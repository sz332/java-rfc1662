package com.acme.rfc1662.impl;

import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IPacketInformation;
import com.acme.rfc1662.IPacketInformationSerializer;

public class PacketInformationSerializer implements IPacketInformationSerializer {

	@Override
	public byte[] convertPacketInformation(IPacketInformation information) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		bos.write(information.getAddress());
		bos.write(information.getControl());
		bos.write(information.getProtocol(), 0, information.getProtocol().length);
		bos.write(information.getInformation(), 0, information.getInformation().length);

		return bos.toByteArray();
	}

}
