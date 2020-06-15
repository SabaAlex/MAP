package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class ValueExp implements Exp{

    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString()
    {
        return e.toString();
    }
}
