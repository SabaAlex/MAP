package repo;

import exceptions.MyException;
import model.state.PrgState;

import java.util.List;

public interface IRepo {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgStateList);
    void logPrgStateExec(PrgState prgToPrint) throws MyException;
    void setlogFilePath(String logFilePath);
    void addProgram(PrgState newState);
    PrgState getPrgStateWithId(int id);
}
