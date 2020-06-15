package model.statements;

import exceptions.MyException;
import model.expressions.Exp;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyIDictionary;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ReadFileStmt implements IStmt {
    Exp exp;
    String varName;

    public ReadFileStmt(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState programState) throws MyException {

        MyIDictionary<String, Value> symTbl = programState.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        MyIHeap<Integer, Value> heap = programState.getHeap();

        if(!(symTbl.isDefined(varName)))
            throw new MyException("The variable " + varName + " was not declared before!");

        Value varValue = symTbl.getValue(varName);

        if(!(varValue.getType() instanceof IntType))
            throw new MyException("Type of variable " + varName + " should be an IntegerType!");

        Value valueFilePath = exp.eval(symTbl, heap);

        if(!(valueFilePath instanceof StringValue))
            throw new MyException("Type of variable " + varName + " should be an StringType!");

        if(!(fileTable.isDefined((StringValue)valueFilePath)))
            throw new MyException("File descriptor does not exist!");

        BufferedReader fileDescriptor = fileTable.getValue((StringValue)valueFilePath);

        try {
            String line = fileDescriptor.readLine();
            IntValue newValue;
            if(line == null) {
                newValue = (IntValue)new IntType().defaultValue();
            }
            else {
                newValue = new IntValue(Integer.parseInt(line));
            }
            symTbl.put(varName, newValue);
            return null;
        }
        catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.getValue(varName);
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeVar.equals(new IntType())){

            if (typeExp.equals(new StringType()))
                return typeEnv;

            throw new MyException("File name not a string ");
        }

        throw new MyException("Variable is not int ");
    }

    @Override
    public String toString(){
        return "readFile(" + exp + ", " + varName + ")";
    }
}


