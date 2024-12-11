package model.value;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String val;

    public StringValue(String val) {
        this.val = val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(IValue obj) {
        if (obj instanceof StringValue) {
            return ((StringValue) obj).getVal().equals(val);
        }
        return false;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringValue) {
            return ((StringValue) obj).getVal().equals(val);
        }
        return false;
    }
}
