package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public void setAddress(int address) {
        this.address = address;
    }

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString(){
        return "(" + address + "," + locationType + ")";
    }

    public Value deepCopy() {
        return new RefValue(address, locationType);
    }
}

