package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class CloseRFileStmt implements IStmt {

    Exp filePath;

    public CloseRFileStmt(Exp filePath) {
        this.filePath = filePath;
    }

    @Override
    public PrgState execute(PrgState programState) throws MyException {
        MyIDictionary<String, Value> symTbl = programState.getSymTable();
        MyIHeap<Integer, Value> heap = programState.getHeap();
        MyIDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        Value valueFilePath = filePath.eval(symTbl, heap);

        if(!(valueFilePath.getType() instanceof StringType))
            throw new MyException("File Path should be a StringType!");

        if(!(fileTable.isDefined((StringValue) valueFilePath)))
            throw new MyException("File Path does not exist!");

        BufferedReader fileDescriptor = fileTable.getValue((StringValue) valueFilePath);

        try {
            fileDescriptor.close();
            fileTable.remove((StringValue)valueFilePath);
            return null;
        }
        catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(filePath.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = filePath.typeCheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;
        throw new MyException("The Closing expression type is not a string");
    }

    @Override
    public String toString(){
        return "closeRFile(" + filePath + ")";
    }
}
