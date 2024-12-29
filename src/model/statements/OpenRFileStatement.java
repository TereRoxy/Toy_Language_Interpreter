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
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement {
    private IExpression exp;

    public OpenRFileStatement(IExpression exp) {
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
        if (fileTable.contains(stringValue)) {
            throw new StatementException("File is already opened.");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stringValue.getVal()));
            fileTable.insert(stringValue, bufferedReader);
        } catch (IOException e) {
            throw new StatementException("Error opening file: " + e.getMessage());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        IType type = exp.typecheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new TypeException("Expression is not of type String.");
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
}
