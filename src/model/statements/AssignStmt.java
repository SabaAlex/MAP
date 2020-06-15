package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class AssignStmt implements IStmt{

    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String toString(){
        return id+" = "+ exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value val = exp.eval(symTbl, heap);

        if (symTbl.isDefined(id)){
            Type typId = (symTbl.getValue(id)).getType();
            if (val.getType().equals(typId))
                symTbl.put(id, val);
            else throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match");
        }
        else throw new MyException("the used variable " +id + " was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type var = typeEnv.getValue(id);
        Type exp = this.exp.typeCheck(typeEnv);
        if(var.equals(exp))
            return typeEnv;
        throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}
