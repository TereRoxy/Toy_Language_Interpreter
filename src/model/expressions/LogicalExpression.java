package model.expressions;

import exception.ExpressionException;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;
import exception.ADTException;
import exception.KeyNotFoundException;
import model.adt.MyIDictionary;

public class LogicalExpression implements IExpression{
    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;

    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException, ExpressionException {
        IValue leftValue = left.eval(symTable, heap);
        IValue rightValue = right.eval(symTable, heap);

        if (!leftValue.getType().equals(new BoolType()) ){
            throw new ExpressionException("Left operand is not a BoolType");
        }
        if (!rightValue.getType().equals(new BoolType()) ){
            throw new ExpressionException("Right operand is not a BoolType");
        }

        switch (operator) {
            case AND:
                return new BoolValue(((BoolValue)leftValue).getValue() && ((BoolValue)rightValue).getValue());
            case OR:
                return new BoolValue(((BoolValue)leftValue).getValue() || ((BoolValue)rightValue).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new ExpressionException("Second operand is not a BoolType");
            }
        } else {
            throw new ExpressionException("First operand is not a BoolType");
        }
    }

    public String toString() {
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }

}
