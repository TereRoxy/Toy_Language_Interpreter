package model.expressions;

import exception.KeyNotFoundException;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.adt.MyIDictionary;
import model.types.IType;
import model.value.IValue;
import exception.ADTException;

public class VariableExpression implements IExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, KeyNotFoundException {
        return symTable.getValue(name);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(name);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws KeyNotFoundException {
        return typeEnv.getValue(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
