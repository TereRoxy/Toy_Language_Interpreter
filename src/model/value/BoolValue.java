package model.value;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(IValue obj) {
        if (obj instanceof BoolValue) {
            return ((BoolValue) obj).getValue() == value;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
