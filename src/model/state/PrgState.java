package model.state;

import exceptions.MyException;
import model.exeStack.MyIStack;
import model.exeStack.MyStack;
import model.heap.MyHeap;
import model.heap.MyIHeap;
import model.out.MyIList;
import model.out.MyList;
import model.statements.IStmt;
import model.symTable.MyDictionary;
import model.symTable.MyIDictionary;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class PrgState {

    static int ids;
    int prgId;
    MyIStack<IStmt> executionStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIHeap<Integer, Value> heap;
    MyIDictionary<StringValue, BufferedReader> fileTable;

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Integer, Value> heap) {
        this.heap = heap;
    }

    private synchronized int getNewId(){
        ids++;
        return ids;
    }

    public PrgState(IStmt statement){
        executionStack = new MyStack<IStmt>();
        symbolTable = new MyDictionary<String, Value>();
        heap = new MyHeap<Integer, Value>();
        out = new MyList<Value>();
        fileTable = new MyDictionary<StringValue, BufferedReader>();
        originalProgram = statement;
        prgId = getNewId();

        executionStack.push(statement);
    }

    public void resetStateToOriginal(){
        executionStack = new MyStack<IStmt>();
        symbolTable = new MyDictionary<String, Value>();
        heap = new MyHeap<Integer, Value>();
        out = new MyList<Value>();
        fileTable = new MyDictionary<StringValue, BufferedReader>();

        executionStack.push(originalProgram);
    }

    @Override
    public String toString() {
        return "ProgramState:" + "\n" +
                "Id:" + prgId + "\n" +
                "ExeStack:\n" + executionStack +"\n"+
                "SymbolsTable:\n" + symbolTable + "\n" +
                "Heap:\n" + heap + "\n" +
                "Out:\n" + out +"\n" +
                "FileTable:\n" + fileTable + "\n";
    }

    public int getPrgId() {
        return prgId;
    }

    public PrgState oneStep() throws MyException {

        if(executionStack.isEmpty()) throw new MyException("stack empty");

        IStmt crtStmt = executionStack.pop();
        return crtStmt.execute(this);
    }

    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    public PrgState GetCurrentState()
    {
        return this;
    }

    ///getters and setters
    public MyIStack<IStmt> getExeStack() {
        return executionStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.executionStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symbolTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symbolTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

}
