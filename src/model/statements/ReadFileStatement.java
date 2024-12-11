package model.statements;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import exception.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IntType;
import model.types.StringType;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private IExpression exp;
    private String varName;

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

        if(read.equals("")){
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

    public String toString(){
        return "ReadFile(" + exp.toString() + ", " + varName + ")";
    }
}
