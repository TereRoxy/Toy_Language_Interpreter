package model.expressions;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

// Represents a relational expression
// A relational expression is an expression that compares two values
// and returns a boolean value
// Example: exp1 < exp2
// Example: exp1 <= exp2
// Example: exp1 == exp2
// Example: exp1 != exp2
// Example: exp1 > exp2
// Example: exp1 >= exp2


public class RelationalExpression implements IExpression{
    private final IExpression left;
    private final IExpression right;
    private final RelationalOperator operator;

    // Constructor
    public RelationalExpression(IExpression left, IExpression right, RelationalOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }


    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException, ExpressionException {
        IValue exp1 = left.eval(symTable, heap);
        IValue exp2 = right.eval(symTable, heap);

        // Check if the two expressions are of the same type and if they are integers
        if (!exp1.getType().equals(exp2.getType())) {
            throw new ExpressionException("The two expressions are not of the same type");
        }

        if ( !exp1.getType().equals(new IntType()) ){
            throw new ExpressionException("The left expression is not an integer");
        }

        if ( !exp2.getType().equals(new IntType()) ){
            throw new ExpressionException("The right expression is not an integer");
        }

        //cast to IntValue
        IntValue leftInt = (IntValue)exp1;
        IntValue rightInt = (IntValue)exp2;

        // Compare the two values
        switch(operator){
            case LESS:
                return new BoolValue(leftInt.getValue() < rightInt.getValue());
            case LESS_OR_EQUAL:
                return new BoolValue(leftInt.getValue() <= rightInt.getValue());
            case EQUAL:
                return new BoolValue(leftInt.getValue() == rightInt.getValue());
            case NOT_EQUAL:
                return new BoolValue(leftInt.getValue() != rightInt.getValue());
            case GREATER:
                return new BoolValue(leftInt.getValue() > rightInt.getValue());
            case GREATER_OR_EQUAL:
                return new BoolValue(leftInt.getValue() >= rightInt.getValue());
            default:
                throw new ExpressionException("Invalid relational operator");
        }

    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), right.deepCopy(), operator);
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

    @Override
    public String toString() {
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }

}
