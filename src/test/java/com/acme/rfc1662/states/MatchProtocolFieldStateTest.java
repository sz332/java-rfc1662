package com.acme.rfc1662.states;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.acme.rfc1662.IEscapeDecoder;
import com.acme.rfc1662.IPacketInformation;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.IParsingStateMachine.State;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingContextConfig;

public class MatchProtocolFieldStateTest {

	@Test
	public void testOneByte() {
		IParsingStateMachine machine = mock(IParsingStateMachine.class);
		IParsingContext context = mock(IParsingContext.class);
		IParsingContextConfig config = mock(IParsingContextConfig.class);
		IPacketInformation packetInformation = mock(IPacketInformation.class);
		IEscapeDecoder decoder = mock(IEscapeDecoder.class);
		
		when(decoder.read(any())).thenReturn(5);
		when(config.getDecoder()).thenReturn(decoder);
		when(config.getFcs()).thenReturn(FrameCheckSequence.TWO_OCTET);
		when(config.getProtocol()).thenReturn(Protocol.ONE_OCTET);
		when(context.config()).thenReturn(config);
		when(context.packetInformation()).thenReturn(packetInformation);
		
		MatchProtocolFieldState state = new MatchProtocolFieldState();
		state.doAction(machine, context);
		
		verify(packetInformation).setProtocol(new byte[] {5});
		verify(machine).setState(State.READ_UNTIL_END_FLAG_STATE);
	}
	
}
