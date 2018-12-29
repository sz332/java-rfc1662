package com.acme.rfc1662;

public interface IStateMachine {

    void setState(Class<? extends IState> stateClass, IInputContext context, IOutputContext outputContext, ITemporaryContext tempContext);

}
