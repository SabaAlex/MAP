package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {

    boolean val;

    public BoolValue(boolean v){val = v;}

    public boolean getVal(){return val;}

    public String toString() {return String.valueOf(val);}

    public Type getType() {return new BoolType();}

    public boolean equals(Object object){
        boolean typesEqual = object instanceof StringValue;
        if(typesEqual)
            return val == ((BoolValue)object).getVal();
        return false;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

}