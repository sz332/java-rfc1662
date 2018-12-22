package com.acme.rfc1662.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.acme.rfc1662.IPPPEncoder;
import com.acme.rfc1662.IPacketInformationSerializer;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;

public class PPPEncoder implements IPPPEncoder {

	private static final int ETX = 0x03;
	private static final int FIELD_FLAG = 0x7E;
	private static final int FIELD_ADDRESS = 0xFF;
	private static final int CONTROL_ESCAPE = 0x7D;
	private static final int FIELD_CONTROL = 0x03;
	private static final int XOR_VALUE = 0x20;

	IPacketInformationSerializer serializer = new PacketInformationSerializer();

	Protocol protocol;
	FrameCheckSequence fcs;

	public PPPEncoder(Protocol protocol, FrameCheckSequence fcs) {
		this.protocol = protocol;
		this.fcs = fcs;
	}

	@Override
	public byte[] encode(byte[] content) {

		PacketInformation packetInformation = new PacketInformation();
		packetInformation.setAddress(FIELD_ADDRESS);
		packetInformation.setControl(FIELD_CONTROL);
		packetInformation.setProtocol(protocol.identifier());
		packetInformation.setInformation(content);

		int crc = fcs.calculator().calculate(serializer.convertPacketInformation(packetInformation));
		byte[] crcArray = convertCrc(crc, fcs.lengthInBytes());
	
		byte[] escaped = escape(calculateFullMessage(FIELD_ADDRESS, FIELD_CONTROL, protocol.identifier(), content, crcArray));
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(FIELD_FLAG);
		bos.write(escaped, 0, escaped.length);
		bos.write(FIELD_FLAG);

		return bos.toByteArray();
	}

	private byte[] calculateFullMessage(int fieldAddress, int fieldControl, byte[] protocol, byte[] content, byte[] crcArray) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		bos.write(fieldAddress);
		bos.write(fieldControl);
		bos.write(protocol, 0, protocol.length);
		bos.write(content, 0, content.length);		
		bos.write(crcArray, 0, crcArray.length);
			
		return bos.toByteArray();
	}
	
	private byte[] convertCrc(int crc, int lengthInBytes) {
		
		if (lengthInBytes == 2) {
			return ByteBuffer.allocate(lengthInBytes).order(ByteOrder.LITTLE_ENDIAN).putShort((short)crc).array();
		} else {
			return ByteBuffer.allocate(lengthInBytes).order(ByteOrder.LITTLE_ENDIAN).putInt(crc).array();
		}
	}

	private byte[] escape(byte[] data) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		for (byte b : data) {
			if (b == FIELD_FLAG || b == CONTROL_ESCAPE || b == ETX) {
				bos.write(CONTROL_ESCAPE);
				bos.write(b ^ XOR_VALUE);
			} else {
				bos.write(b);
			}
		}

		return bos.toByteArray();
	}

}
