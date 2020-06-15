package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class HeapWriteStmt implements IStmt {
    String varName;
    Exp exp;

    public HeapWriteStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTbl.isDefined(varName)){

            if (symTbl.getValue(varName).getType() instanceof RefType){

                int address = ((RefValue) symTbl.getValue(varName)).getAddress();
                if (heap.isDefined(address)){

                    Value value = exp.eval(symTbl, heap);
                    if (value.getType().equals(heap.getValue(address).getType())){

                        heap.replace(address, value);
                    }
                }
                else throw new MyException(varName + "does not have a valid address");
            }
            else throw new MyException(varName + "is not a ref type value");
        }
        else throw new MyException(varName + " does not exist");

        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varName + "," + exp + ')';
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(varName, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type typeVar = typeEnv.getValue(varName);
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;

        throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}
