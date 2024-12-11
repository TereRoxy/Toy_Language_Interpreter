package model.expressions;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.adt.MyIDictionary;
import model.value.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException, ExpressionException {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
