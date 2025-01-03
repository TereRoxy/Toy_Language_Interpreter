package model.statements;

import exception.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;

public class VariableDecStatement implements IStatement {
    private final String name;
    private final IType type;

    public VariableDecStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.contains(name)) {
            throw new StatementException("Variable " + name + " is already declared.");
        }
        symTable.insert(name, type.defaultValue());
        return null;
    }

    @Override
    public String toString() {
        return "VariableDecStatement{" +
                "name='" + name + '\'' + ", type=" + type +
                '}';
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDecStatement(name, type);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        typeEnv.insert(name, type);
        return typeEnv;
    }
}
