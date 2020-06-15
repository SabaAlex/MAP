package model.statements;

import exceptions.MyException;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    MyIDictionary<String,Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
