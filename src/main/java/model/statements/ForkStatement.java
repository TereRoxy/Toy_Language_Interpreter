package model.statements;

import model.adt.*;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStatement> newExeStack = new MyStack<>();
        MyIDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();
        MyIList<IValue> output = state.getOutput();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        return new PrgState(newSymTable, newExeStack, output, statement, fileTable, heap);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        return statement.typecheck(typeEnv);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
