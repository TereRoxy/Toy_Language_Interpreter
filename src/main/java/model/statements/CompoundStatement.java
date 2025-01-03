package model.statements;

import model.adt.MyIDictionary;
import model.state.PrgState;
import exception.StatementException;
import model.types.IType;

public class CompoundStatement implements IStatement{
    private final IStatement statement1;
    private final IStatement statement2;

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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        return statement2.typecheck(statement1.typecheck(typeEnv));
    }

    public String toString() {
        return statement1.toString() + "; " + statement2.toString();
    }

}
