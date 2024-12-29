package model.value;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{

    final int address;
    final IType locationType;

    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(IValue obj) {
        return obj instanceof RefValue && ((RefValue) obj).getLocationType().equals(this.locationType) && ((RefValue) obj).getAddress() == this.address;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
