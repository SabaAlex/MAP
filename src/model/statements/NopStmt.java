package model.statements;

import exceptions.MyException;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.Type;

public class NopStmt implements IStmt {

    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "";
    }
}
