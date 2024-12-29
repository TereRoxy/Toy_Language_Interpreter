package model.types;

import model.value.StringValue;

public class StringType implements IType{

    public StringType() {
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof StringType;
    }

    public String toString() {
        return "String";
    }

    //default value for string
    public StringValue defaultValue() {
        return new StringValue("");
    }
}
