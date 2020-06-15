package model.statements;

import exceptions.MyException;
import model.exeStack.MyIStack;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStmt implements IStmt{

    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp=e;
        thenS=t;
        elseS=el;
    }

    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()
            +")ELSE("+elseS.toString()+"))";
    }

    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value cond = exp.eval(symTbl, heap);

        if(!cond.getType().equals(new BoolType())) {
            throw new MyException("No a boolean condition");
        }

        else {
            BoolValue boolCond = (BoolValue) cond;

            if(boolCond.getVal() == true)
                stack.push(thenS);
            else
                stack.push(elseS);
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typeExp = exp.typeCheck(typeEnv);

        if (typeExp.equals(new BoolType())){

            thenS.typeCheck(typeEnv);
            elseS.typeCheck(typeEnv);
            return typeEnv;
        }

        throw new MyException("The condition of IF does not have the bool type");
    }
}

