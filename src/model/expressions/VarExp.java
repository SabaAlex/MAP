package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class VarExp implements Exp{

    String id;

    public VarExp(String id) {
        this.id = id;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {

        if (!tbl.isDefined(id))
            throw new MyException("Variable " + id + " is not defined");

        return tbl.getValue(id);

    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.getValue(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

