package model.statements;

import exception.*;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStatement implements IStatement{
    private final IStatement thenS;
    private final IStatement elseS;
    private final IExpression expression;

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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        IType typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new BoolType())){
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new TypeException("The condition of IF has not the type bool");
        }
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
