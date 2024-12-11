package model.statements;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import exception.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;


public class AssignStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException, KeyNotFoundException {
        if (!state.getSymTable().contains(variableName)) {
            throw new StatementException("Variable " + variableName + " is not defined");
        }
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        IValue evalValue = this.expression.eval(state.getSymTable(), state.getHeap());

        if(!value.getType().equals(evalValue.getType())){
            throw new StatementException("Type of expression and type of variable are not the same");
        }
        state.getSymTable().insert(variableName, evalValue);
        //return state;
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(variableName, expression);
    }

    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
