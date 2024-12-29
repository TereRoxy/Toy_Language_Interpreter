package model.expressions;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticalExpression implements IExpression{
    private final IExpression left;
    private final IExpression right;
    private final ArithmeticalOperator operator;

    public ArithmeticalExpression(IExpression left, IExpression right, ArithmeticalOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException, ExpressionException {
        IValue leftValue = left.eval(symTable, heap);
        IValue rightValue = right.eval(symTable, heap);

        if( !leftValue.getType().equals(new IntType()) ){
            throw new ExpressionException("Left operand is not an integer");
        }
        if( !rightValue.getType().equals(new IntType()) ){
            throw new ExpressionException("Right operand is not an integer");
        }

        IntValue leftInt = (IntValue)leftValue;
        IntValue rightInt = (IntValue)rightValue;

        switch (operator) {
            case ADD:
                return new IntValue(leftInt.getValue() + rightInt.getValue());
            case SUBTRACT:
                return new IntValue(leftInt.getValue() - rightInt.getValue());
            case MULTIPLY:
                return new IntValue(leftInt.getValue() * rightInt.getValue());
            case DIVIDE:
                if(rightInt.getValue() == 0){
                    throw new ExpressionException("Division by zero");
                }
                return new IntValue(leftInt.getValue() / rightInt.getValue());
            default:
                throw new ExpressionException("Invalid arithmetical operator");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticalExpression(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws KeyNotFoundException, ExpressionException {
        IType leftType = left.typecheck(typeEnv);
        IType rightType = right.typecheck(typeEnv);

        if( !leftType.equals(new IntType()) ){
            throw new ExpressionException("Left operand is not an integer");
        }
        if( !rightType.equals(new IntType()) ){
            throw new ExpressionException("Right operand is not an integer");
        }

        return new IntType();
    }

    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }


}
