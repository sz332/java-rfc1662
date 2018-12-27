package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;
import java.util.EnumMap;

import com.acme.rfc1662.EndOfStreamException;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.ParserResult;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.states.EndOfStreamState;
import com.acme.rfc1662.states.MatchAddressFieldState;
import com.acme.rfc1662.states.MatchControlFieldState;
import com.acme.rfc1662.states.MatchProtocolOneOctetFieldState;
import com.acme.rfc1662.states.MatchProtocolTwoOctetFieldState;
import com.acme.rfc1662.states.ParseValidMessageState;
import com.acme.rfc1662.states.ReadUntilEndingFlagState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;
import com.acme.rfc1662.states.UnescapeState;
import com.acme.rfc1662.states.ValidateChecksumState;

public final class ParsingStateMachine implements IParsingStateMachine {

    final EnumMap<State, IParsingState> states;
    final Protocol protocol;
    final FrameCheckSequence fcs;
    
    public ParsingStateMachine(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
        this.states = new EnumMap<>(State.class);
    }

    private void initStates() {
        states.put(State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE, new ReadUntilFirstMatchingFlagState());
        states.put(State.READ_UNTIL_END_FLAG_STATE, new ReadUntilEndingFlagState());
        states.put(State.UNESCAPE_STATE, new UnescapeState());
        states.put(State.VALIDATE_CHECKSUM_STATE, new ValidateChecksumState());
        states.put(State.MATCH_ADDRESS_FIELD_STATE, new MatchAddressFieldState());
        states.put(State.MATCH_CONTROL_FIELD_STATE, new MatchControlFieldState());
        states.put(State.MATCH_PROTOCOL_ONE_OCTET_FIELD_STATE, new MatchProtocolOneOctetFieldState());
        states.put(State.MATCH_PROTOCOL_TWO_OCTET_FIELD_STATE, new MatchProtocolTwoOctetFieldState());
        states.put(State.PARSE_VALID_MESSAGE_STATE, new ParseValidMessageState());
        states.put(State.END_OF_STREAM_STATE, new EndOfStreamState());
    }

    public ParserResult parse(final ByteArrayInputStream inputStream) {
        
        if (this.states.isEmpty()) {
            this.initStates();
        }

        inputStream.mark(0);

        final IParsingContext context = new ParsingContext(
                    new ParsingContextConfig(new ByteArrayInputStreamReader(), protocol, fcs),
                    inputStream);

        this.setState(State.READ_UNTIL_FIRST_MATCHING_FLAG_STATE, context);

        return new ParserResult(context.result().getRemaining(), context.result().getMessages());
    }

    @Override
    public void setState(final State state, final IParsingContext context) {
        try {
            final IParsingState newState = states.get(state);
            newState.doAction(this, context);
        } catch (final EndOfStreamException e) {
            this.setState(State.END_OF_STREAM_STATE, context);
        }
    }

}
