package com.acme.rfc1662.states;

import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParseStateMachine;
import com.acme.rfc1662.PacketInformation;

public class ReadUntilEndingFlagState extends AbstractState {

	private static final int FIELD_FLAG = 0x7E;

	public ReadUntilEndingFlagState(PacketInformation packetInformation) {
		super(packetInformation);
	}

	public void doAction(IParseStateMachine machine, IParsingContext context) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int data = -1;

		do {
			data = context.getDecoder().read(context.getInputStream());

			if (data != FIELD_FLAG) {
				bos.write(data);
			}

		} while (data != FIELD_FLAG);
		
		context.getInputStream().mark(0);

		machine.setState(new SeparateInformationFromChecksumState(packetInformation, bos.toByteArray()));
	}

}
