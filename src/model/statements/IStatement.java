package model.statements;

import model.state.PrgState;

public interface IStatement {
    PrgState execute(PrgState state) throws Exception;
    IStatement deepCopy();
}
