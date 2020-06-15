package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.operators.LogicOp;
import model.symTable.MyIDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp {

    Exp firstExpression;
    Exp secondExpression;
    LogicOp op;// 1 - and, 2 - or

    public LogicExp(LogicOp operator, Exp e1, Exp e2) {

        this.firstExpression = e1;
        this.secondExpression = e2;
        this.op = operator;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {

        Value v1, v2;
        v1 = firstExpression.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = secondExpression.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;

                boolean b1, b2;
                b1 = i1.getVal();
                b2 = i2.getVal();

                switch (this.op){
                    case OR:
                        return new BoolValue(b1 || b2);
                    case AND:
                        return new BoolValue(b1 && b2);
                }
            }
            else
                throw new MyException("second operand is not a boolean");
        }
        else
            throw new MyException("first operand is not a boolean");

        return new BoolValue(true);

    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(op, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnv);
        type2 = secondExpression.typeCheck(typeEnv);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        switch (op){
            case AND:
                return firstExpression.toString() + " && " + secondExpression.toString();
            case OR:
                return firstExpression.toString() + " || " + secondExpression.toString();
            default:
                return "";
        }

    }

}
