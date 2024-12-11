package model.statements;

import model.state.PrgState;
import model.statements.IStatement;

public class NopStatement implements IStatement {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "nop";
    }


}
