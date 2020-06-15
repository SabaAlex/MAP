package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {

    int val;

    public IntValue(int v){val = v;}

    public int getVal(){return val;}

    public String toString() {return String.valueOf(val);}

    public Type getType() {return new IntType();}

    public boolean equals(Object object){
        boolean typesEqual = object instanceof StringValue;
        if(typesEqual)
            return val == ((IntValue)object).getVal();
        return false;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

}
