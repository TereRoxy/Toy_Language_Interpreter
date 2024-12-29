package controller;

import exception.EmptyStackException;
import model.state.PrgState;
import repository.IRepo;

import java.util.List;

public interface IController {
 //   PrgState oneStep(PrgState state) throws Exception;
    void allStep() throws Exception;
//    void displayState() throws Exception;
//    PrgState getCrtState();
    IRepo getRepo();
    void addPrgState(PrgState state);
    void setRepo(IRepo r);
//    void oneStepWrapper() throws Exception;
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    void oneStepForAllPrg(List<PrgState> prgList) throws RuntimeException, InterruptedException;

    void initializeExecutor();
    void closeExecutor();

    boolean areProgramsFinished();
}
