package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException;
    Exp deepCopy();
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
