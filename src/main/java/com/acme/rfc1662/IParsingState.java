package com.acme.rfc1662;

public interface IParsingState {

    void doAction(IParsingStateMachine machine, IParsingContext context);

}
