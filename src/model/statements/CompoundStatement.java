package model.statements;

import model.state.PrgState;
import exception.StatementException;

public class CompoundStatement implements IStatement{
    private IStatement statement1;
    private IStatement statement2;

    public CompoundStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        state.getExeStack().push(statement2);
        state.getExeStack().push(statement1);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(statement1.deepCopy(), statement2.deepCopy());
    }

    public String toString() {
        return statement1.toString() + "; " + statement2.toString();
    }

}
