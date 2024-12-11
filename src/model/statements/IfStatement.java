package model.statements;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import exception.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import model.types.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStatement implements IStatement{
    private IStatement thenS;
    private IStatement elseS;
    private IExpression expression;

    public IfStatement(IExpression expression, IStatement thenS, IStatement elseS) {
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, KeyNotFoundException, ExpressionException {
        IValue result = expression.eval(state.getSymTable(), state.getHeap());
        //if (result.getType().equals(new BoolType()) ){
        if (!(result instanceof BoolValue)){
            throw new StatementException("Expression is not a boolean");
        }

        BoolValue boolResult = (BoolValue) result;

        if (boolResult.getValue()){
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    public IStatement getThenS() {
        return thenS;
    }

    public IStatement getElseS() {
        return elseS;
    }

    public String toString() {
        return "if(" + expression.toString() + ") then {" + thenS.toString() + "} else{" + elseS.toString() + "}";
    }



}
