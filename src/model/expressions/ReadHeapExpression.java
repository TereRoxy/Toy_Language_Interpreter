package model.expressions;

import exception.ADTException;
import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.IValue;
import model.value.RefValue;
import model.types.RefType;

public class ReadHeapExpression implements IExpression {
    private IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ExpressionException, KeyNotFoundException, ADTException {
        IValue value = expression.eval(symTable, heap);
        if (!(value instanceof RefValue)) {
            throw new ExpressionException("Expression is not of RefType");
        }
        int address = ((RefValue) value).getAddress();
        if (!heap.contains(address)) {
            throw new KeyNotFoundException("Address not found in heap");
        }
        return heap.getValue(address);
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "ReadHeap(" + expression.toString() + ")";
    }
}