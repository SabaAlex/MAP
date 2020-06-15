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
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
    Exp filePath;

    public OpenRFileStmt(Exp filePath) {
        this.filePath = filePath;
    }

    @Override
    public PrgState execute(PrgState programState) throws MyException {

        MyIDictionary<String, Value> symbTbl = programState.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        MyIHeap<Integer, Value> heap = programState.getHeap();

        Value valueFilePath = filePath.eval(symbTbl, heap);

        if(!(valueFilePath.getType() instanceof StringType))
            throw new MyException("File Path should be a StringType!");

        if(symbTbl.isDefined(((StringValue)valueFilePath).getVal()))
            throw new MyException("File descriptor already defined!");

        try {
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(((StringValue) valueFilePath).getVal()));
            fileTable.put((StringValue)valueFilePath, fileDescriptor);
            return null;
        }
        catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(filePath.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = filePath.typeCheck(typeEnv);

        if (type.equals(new StringType()))
            return typeEnv;

        throw new MyException("The Reading expression type is not a string");
    }

    @Override
    public String toString(){
        return "openRFile(" + filePath + ")";
    }
}

