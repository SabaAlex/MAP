package model.statements;

import exceptions.MyException;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class VarDeclStmt implements IStmt{

    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(name)) throw new MyException("already defined");
        else symTbl.put(name, typ.defaultValue());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ + " " + name;

    }
}
