package model.statements;

import exceptions.MyException;
import model.heap.MyIHeap;
import model.out.MyIList;
import model.state.PrgState;
import model.symTable.MyDictionary;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt {

    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIList<Value> out = state.getOut();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        MyIDictionary<String, Value> newSymTbl = new MyDictionary<String, Value>();
        for (Map.Entry<String, Value> entry: symTbl.getContainer().entrySet()){
            newSymTbl.put(entry.getKey(), entry.getValue());
        }

        PrgState newState = new PrgState(stmt);
        newState.setSymTable(newSymTbl);
        newState.setHeap(heap);
        newState.setOut(out);
        newState.setFileTable(fileTable);

        return newState;
    }

    @Override
    public String toString(){
        return "fork" + stmt + "";
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typeCheck(typeEnv);
        return typeEnv;
    }
}
