package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.out.MyIList;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class PrintStmt implements IStmt{

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    Exp exp;

    public String toString(){
        return "print(" +exp.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException {

        MyIList<Value> ot = state.getOut();
        MyIDictionary<String, Value> symt = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        ot.add(exp.eval(symt, heap));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typeCheck(typeEnv);

        return typeEnv;
    }
}
