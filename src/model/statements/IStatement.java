package model.statements;

import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public interface IStatement {
    PrgState execute(PrgState state) throws Exception;
    IStatement deepCopy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String,IType> typeEnv) throws Exception;
}
