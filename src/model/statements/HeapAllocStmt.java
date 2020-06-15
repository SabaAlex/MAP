package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.*;
import model.values.*;

public class HeapAllocStmt implements IStmt {
    String varName;
    Exp exp;

    public HeapAllocStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTbl.isDefined(varName)){

            if (symTbl.getValue(varName).getType() instanceof RefType){

                Value expVal = exp.eval(symTbl, heap);
                RefType inner = (RefType)symTbl.getValue(varName).getType();

                if (inner.getInner().equals(expVal.getType())){

                    boolean found = false;
                    int index = 1;
                    while (!found){
                        if (heap.isDefined(index)) index++;
                        else found = true;
                    }

                    if (!inner.getInner().equals(expVal.getType()))
                        throw new MyException("types don't match");

                    Value newRef = expVal.deepCopy();

                    heap.newAlloc(index, newRef);
                    RefValue symRef = (RefValue)symTbl.getValue(varName);
                    symRef.setAddress(index);
                }
                else throw new MyException(varName + " type don't match!");
            }
            else throw new MyException(varName + " not ref type!");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocStmt(varName, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typeVar = typeEnv.getValue(varName);
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;

        throw new MyException("Assignment: right hand side and left hand side have different types ");

    }

    @Override
    public String toString() {
        return "new(" + varName + "," + exp + ')';
    }
}
