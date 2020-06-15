package model.statements;

import exceptions.MyException;
import javafx.scene.Parent;
import model.exeStack.MyIStack;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements IStmt {
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> stack = state.getExeStack();

        Value cond = exp.eval(symTbl, heap);
        if (cond.getType().equals(new BoolType())){
            BoolValue condVal = (BoolValue) cond;
            if (condVal.getVal()){
                stack.push(this);
                stack.push(stmt);
            }
        }
        else throw new MyException("while expr is not bool type");

        return null;
    }

    @Override
    public String toString(){
        return "while(" + exp + ")" + stmt + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeExp.equals(new BoolType())){
            stmt.typeCheck(typeEnv);
            return typeEnv;
        }

        throw new MyException("The condition of WHILE has not the type bool");
    }
}
