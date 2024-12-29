package repository;

import exception.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepo {
    List<PrgState> getProgramList();
    void setProgramList(List<PrgState> prgList);
//    PrgState getCurrentProgram();
    void setCurrentProgram(PrgState prg);
    List<PrgState> getStates();
    void addPrgState(PrgState state);
    void logPrgStateExec(PrgState state) throws RepoException;
    boolean isEmpty();
}
