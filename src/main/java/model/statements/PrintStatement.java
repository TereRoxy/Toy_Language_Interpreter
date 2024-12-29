package model.statements;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import model.types.IType;
import model.value.IValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, KeyNotFoundException, ExpressionException {
        IValue result = expression.eval(state.getSymTable(), state.getHeap());
        //state.getOutput().add(result.toString());
        state.getOutput().add(result);
        return null;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
