package com.acme.rfc1662;

public interface IState {

    void doAction(IStateMachine machine, IInputContext context, IOutputContext outputContext, ITemporaryContext tempContext);

}
