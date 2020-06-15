package model.statements;

import exceptions.MyException;
import model.exeStack.MyIStack;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.Type;

public class CompStmt implements IStmt {

    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {

        return "("+first.toString() + ";" + second.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
