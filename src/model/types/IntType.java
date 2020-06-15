package model.types;


import model.values.IntValue;
import model.values.Value;

public class IntType implements Type{

    public IntType() {
    }

    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    public Value defaultValue()
    {
        return new IntValue(0);
    }

    public String toString() { return "int";}


}
