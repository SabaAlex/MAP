package model.expressions;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.symTable.MyIDictionary;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExp implements Exp {
    Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {

        if (exp.eval(tbl, heap).getType() instanceof RefType){

            int address = ((RefValue)exp.eval(tbl, heap)).getAddress();
            if (!heap.isDefined(address))
                throw new MyException(exp.eval(tbl, heap) + "does not have a valid address");
            return heap.getValue(address);
        }
        else throw new MyException(exp.eval(tbl, heap) + " not a ref type value");
    }

    public String toString() {
        return "rH(" + exp.toString() + ')';
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type;
        type = exp.typeCheck(typeEnv);

        if (type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }

        throw new MyException("the rH argument is not a Ref Type");
    }
}
