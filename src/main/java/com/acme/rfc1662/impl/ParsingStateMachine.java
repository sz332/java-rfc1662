package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.acme.rfc1662.EndOfStreamException;
import com.acme.rfc1662.IParsingContext;
import com.acme.rfc1662.IParsingState;
import com.acme.rfc1662.IParsingStateMachine;
import com.acme.rfc1662.ParserResult;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.states.EndOfStreamState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;

public final class ParsingStateMachine implements IParsingStateMachine {

    final Map<Class<? extends IParsingState>, IParsingState> states;
    final Protocol protocol;
    final FrameCheckSequence fcs;

    public ParsingStateMachine(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
        this.states = new HashMap<>();
    }

    public ParserResult parse(final ByteArrayInputStream inputStream) {
        inputStream.mark(0);

        final IParsingContext context = new ParsingContext(
                new ParsingContextConfig(
                        new ByteArrayInputStreamReader(), 
                        protocol, 
                        fcs),
                inputStream);

        this.setState(ReadUntilFirstMatchingFlagState.class, context);

        return new ParserResult(context.result().getRemaining(), context.result().getMessages());
    }

    @Override
    public void setState(Class<? extends IParsingState> stateClass, final IParsingContext context) {
        try {

            IParsingState newState = states.get(stateClass);

            if (newState == null) {
                newState = stateClass.newInstance();
                states.put(stateClass, newState);
            }

            newState.doAction(this, context);
        } catch (final EndOfStreamException | InstantiationException | IllegalAccessException e) {
            this.setState(EndOfStreamState.class, context);
        }
    }

}
