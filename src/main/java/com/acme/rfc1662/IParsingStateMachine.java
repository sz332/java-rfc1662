package com.acme.rfc1662;

public interface IParsingStateMachine {

    void setState(Class<? extends IParsingState> stateClass, IParsingContext context);

}
