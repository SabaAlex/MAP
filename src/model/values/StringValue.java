package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {

    String val;

    public StringValue(String v){val = v;}

    public String getVal(){return val;}

    public String toString() {return '"' + val + '"';}

    public Type getType() {return new StringType();}

    @Override
    public boolean equals(Object object){
        boolean typesEqual = object instanceof StringValue;
        if(typesEqual)
            return val == ((StringValue)object).getVal();
        return false;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
