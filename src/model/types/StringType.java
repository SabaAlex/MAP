package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type {

    public StringType() {
    }

    public boolean equals(Object another){
        if (another instanceof StringType)
            return true;
        else
            return false;
    }

    public Value defaultValue()
    {
        return new StringValue("");
    }

    public String toString() { return "string";}
}
