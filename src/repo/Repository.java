package repo;

import exceptions.MyException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

public class  Repository implements  IRepo{

    List<PrgState> listOfPrograms;
    String logFilePath;

    @Override
    public List<PrgState> getPrgList() {
        return listOfPrograms;
    }

    @Override
    public void setPrgList(List<PrgState> prgStateList) {
        this.listOfPrograms = prgStateList;
    }

    public Repository(PrgState prgState){
        listOfPrograms = new Vector<PrgState>();
        listOfPrograms.add(prgState);
    }

    public Repository(String filePath){
        listOfPrograms = new Vector<PrgState>();
        this.logFilePath = filePath;
    }

    @Override
    public void addProgram(PrgState newState) {
        listOfPrograms.add(newState);
    }

    @Override
    public PrgState getPrgStateWithId(int id) {

        for (PrgState prgState: listOfPrograms)
            if(prgState.getPrgId() == id)
                return prgState;
        throw new MyException("no Program with that id found");
    }

    public void setlogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgStateExec(PrgState prgToPrint) throws MyException {
        try{

            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(prgToPrint.toString());
            logFile.write("\n\n\n");
            logFile.close();
        }catch (IOException e){
            throw new MyException(e.getMessage());
        }
    }
}
