package com.acme.rfc1662.impl;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.acme.rfc1662.EndOfStreamException;
import com.acme.rfc1662.IOutputContext;
import com.acme.rfc1662.IInputContext;
import com.acme.rfc1662.IState;
import com.acme.rfc1662.IStateMachine;
import com.acme.rfc1662.ITemporaryContext;
import com.acme.rfc1662.ParsingResult;
import com.acme.rfc1662.enums.FrameCheckSequence;
import com.acme.rfc1662.enums.Protocol;
import com.acme.rfc1662.states.EndOfStreamState;
import com.acme.rfc1662.states.ReadUntilFirstMatchingFlagState;

public final class StateMachine implements IStateMachine {

    final Map<Class<? extends IState>, IState> states;
    final Protocol protocol;
    final FrameCheckSequence fcs;

    private IInputContext inputContext;
    private IOutputContext outputContext;
    private ITemporaryContext tempContext;

    public StateMachine(final Protocol protocol, final FrameCheckSequence fcs) {
        this.protocol = protocol;
        this.fcs = fcs;
        this.states = new HashMap<>();
    }

    public ParsingResult parse(final ByteArrayInputStream inputStream) {
        inputStream.mark(0);

        inputContext = new InputContext(new ContextConfig(new ByteArrayInputStreamReader(), protocol, fcs), inputStream);
        outputContext = new OutputContext();
        tempContext = new TemporaryContext();

        this.setState(ReadUntilFirstMatchingFlagState.class);

        return new ParsingResult(outputContext.getRemaining(), outputContext.getMessages());
    }

    @Override
    public void setState(Class<? extends IState> stateClass) {
        try {

            IState newState = states.get(stateClass);

            if (newState == null) {
                newState = stateClass.newInstance();
                states.put(stateClass, newState);
            }

            newState.doAction(this, inputContext, outputContext, tempContext);
        } catch (final EndOfStreamException | InstantiationException | IllegalAccessException e) {
            this.setState(EndOfStreamState.class);
        }
    }

}
