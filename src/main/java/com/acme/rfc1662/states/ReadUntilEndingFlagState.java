package com.acme.rfc1662.states;

import static com.acme.rfc1662.IParsingStateMachine.State.SEPARATE_INFORMATION_FROM_CHECKSUM_STATE;

import java.io.ByteArrayOutputStream;

import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;

public class ReadUntilEndingFlagState implements IParsingState {

	private static final int FIELD_FLAG = 0x7E;

	@Override
	public void doAction(IParsingStateMachine machine, IParsingContext context) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int data;

		do {
			data = context.config().getDecoder().read(context.inputStream());

			if (data != FIELD_FLAG) {
				bos.write(data);
			}

		} while (data != FIELD_FLAG);

		context.inputStream().mark(0);

		context.packetInformation().setCombinedData(bos.toByteArray());

		machine.setState(SEPARATE_INFORMATION_FROM_CHECKSUM_STATE);
	}

}
