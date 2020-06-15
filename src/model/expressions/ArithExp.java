package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.operators.ArithOp;
import model.symTable.MyIDictionary;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithExp implements Exp{

    Exp firstExpression;
    Exp secondExpression;
    ArithOp op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(ArithOp operator, Exp e1, Exp e2) {

        this.firstExpression = e1;
        this.secondExpression = e2;
        this.op = operator;
    }


    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = firstExpression.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = secondExpression.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();

                switch (this.op){
                    case PLUS:
                        return new IntValue(n1+n2);
                    case MINUS:
                        return new IntValue(n1-n2);
                    case MULTIPLY:
                        return new IntValue(n1*n2);
                    case DIVIDE:
                        if(n2==0)
                            throw new MyException("division by zero");
                        else
                            return new IntValue(n1/n2);
                }
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");

        return new IntValue(0);
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1=firstExpression.typeCheck(typeEnv);
        type2=secondExpression.typeCheck(typeEnv);

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
    public String toString() {
        switch (this.op){
            case PLUS:
                return firstExpression.toString() + " + " + secondExpression.toString();
            case MINUS:
                return firstExpression.toString() + " - " + secondExpression.toString();
            case MULTIPLY:
                return firstExpression.toString() + " * " + secondExpression.toString();
            case DIVIDE:
                return firstExpression.toString() + " / " + secondExpression.toString();
            default:
                return "INVALID!";
        }
    }
}
