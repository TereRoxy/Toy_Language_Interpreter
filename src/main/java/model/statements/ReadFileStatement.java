package model.statements;

import exception.*;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private final IExpression exp;
    private final String varName;

    public ReadFileStatement(IExpression exp, String varName){
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, KeyNotFoundException, ADTException, ExpressionException, IOException {
        var table = state.getSymTable();

        if(!table.contains(varName)){
            throw new StatementException("The variable was not defined");
        }

        if (!table.getValue(varName).getType().equals(new IntType())){
            throw new StatementException("The type is incorrect");
        }

        var res = exp.eval(table, state.getHeap());
        if (!res.getType().equals(new StringType())){
            throw new StatementException("The expression is not a String Type");
        }

        BufferedReader reader = state.getFileTable().getValue((StringValue) res);

        String read = reader.readLine();

        if(read.isEmpty()){
            read = "0";
        }

        int parser = Integer.parseInt(read);
        table.insert(varName, new IntValue(parser));

        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(exp.deepCopy(), varName);
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

    public String toString(){
        return "ReadFile(" + exp.toString() + ", " + varName + ")";
    }
}
