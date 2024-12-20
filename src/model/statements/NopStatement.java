package model.statements;

import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

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
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "nop";
    }


}
