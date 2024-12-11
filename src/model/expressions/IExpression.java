package model.expressions;

import exception.ExpressionException;
import exception.KeyNotFoundException;
import model.adt.MyIHeap;
import model.value.IValue;
import model.adt.MyIDictionary;
import exception.ADTException;

public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException, ExpressionException;
    IExpression deepCopy();
}
