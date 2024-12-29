package model.statements;

import exception.StatementException;
import exception.TypeException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement {
    private final IExpression exp;

    public CloseRFileStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new StatementException("Expression is not of type String.");
        }

        StringValue stringValue = (StringValue) value;
        if (!fileTable.contains(stringValue)) {
            throw new StatementException("File is not opened.");
        }

        BufferedReader bufferedReader = fileTable.getValue(stringValue);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new StatementException("Error closing file: " + e.getMessage());
        }

        fileTable.remove(stringValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        IType expType = exp.typecheck(typeEnv);
        if (!expType.equals(new StringType())) {
            throw new TypeException("Expression is not of type String.");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
