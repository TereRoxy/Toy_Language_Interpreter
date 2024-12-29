package model.types;

import model.value.IntValue;

public class IntType implements IType {
    @Override
    public boolean equals(IType obj) {
        return obj instanceof IntType;
    }


    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IntValue defaultValue() {
        return new IntValue(0);
    }
}
