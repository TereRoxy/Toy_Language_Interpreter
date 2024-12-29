package model.value;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(IValue obj) {
        if (obj instanceof IntValue) {
            return ((IntValue) obj).getValue() == value;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
