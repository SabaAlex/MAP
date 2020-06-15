package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.operators.RelOp;
import model.symTable.MyIDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelExp implements Exp {
    Exp firstExpression;
    Exp secondExpression;
    RelOp op;

    public RelExp(RelOp operator, Exp firstExpression, Exp secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.op = operator;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = firstExpression.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = secondExpression.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int b1, b2;
                b1 = i1.getVal();
                b2 = i2.getVal();

                switch (this.op){
                    case LT:
                        return new BoolValue(b1 < b2);
                    case LTE:
                        return new BoolValue(b1 <= b2);
                    case GT:
                        return new BoolValue(b1 > b2);
                    case GTE:
                        return new BoolValue(b1 >= b2);
                    case EQUAL:
                        return new BoolValue(b1 == b2);
                    case DIFFERENT:
                        return new BoolValue(b1 != b2);
                }
            }
            else
                throw new MyException("second operand is not a int");
        }
        else
            throw new MyException("first operand is not a int");

        return new BoolValue(false);
    }

    @Override
    public Exp deepCopy() {
        return new RelExp(op, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnv);
        type2 = secondExpression.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
            {
                return new IntType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString(){
        switch (this.op){
            case DIFFERENT:
                return firstExpression.toString() + " != " + secondExpression.toString();
            case EQUAL:
                return firstExpression.toString() + " == " + secondExpression.toString();
            case GTE:
                return firstExpression.toString() + " >= " + secondExpression.toString();
            case GT:
                return firstExpression.toString() + " > " + secondExpression.toString();
            case LTE:
                return firstExpression.toString() + " <= " + secondExpression.toString();
            case LT:
                return firstExpression.toString() + " < " + secondExpression.toString();
            default:
                return "";
        }
    }
}
