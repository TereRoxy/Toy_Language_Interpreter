package model.types;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {

    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof RefType && ((RefType) obj).getInner().equals(this.inner);
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
