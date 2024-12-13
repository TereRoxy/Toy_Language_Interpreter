package controller;

import exception.RepoException;
import model.adt.MyIHeap;
import model.state.PrgState;
import model.value.IValue;
import repository.IRepo;
import controller.GarbageCollector;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController{
    private IRepo repo;
    private ExecutorService executor;

    public Controller(IRepo r){
        this.repo = r;
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws RuntimeException, InterruptedException {
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });
        //RUN concurrently one step for each of the existing PrgStates

        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of the new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                        }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //log the changes
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });

        //save the current programs in the repository
        repo.setProgramList(prgList);
    }

    public void allStep() throws Exception{
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getProgramList());
        while(!prgList.isEmpty()){
            //call the conservative garbage collector
            MyIHeap sharedHeap = prgList.getFirst().getHeap(); //the heap is shared between all program states
            Map<Integer, IValue> newHeapContent = GarbageCollector.conservativeGarbageCollector(prgList, sharedHeap);
            prgList.forEach(prg -> prg.getHeap().setContent(newHeapContent)); // update the heap for all program states

            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repo.getProgramList());
        }
        executor.shutdownNow();

        //HERE the repository still contains at least one Completed PrgState
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setProgramList of the repository in order to change the repository

        // update the repository state
        repo.setProgramList(prgList);
    }

    public IRepo getRepo(){
        return repo;
    }

    public void setRepo(IRepo r){
        repo = r;
    }

    public void addPrgState(PrgState state){
        repo.addPrgState(state);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    //    public void displayState() throws Exception {
//        PrgState prg = repo.getCurrentProgram();
//        System.out.println(prg);
//    }

//    public PrgState oneStep(PrgState state) throws Exception {
//        MyIStack<IStatement> stack = state.getExeStack();
//        if (stack.isEmpty()) {
//            throw new EmptyStackException("The PrgState stack is empty");
//        }
//        IStatement crtState = stack.pop();
//        var newState = crtState.execute(state);
//        repo.logPrgStateExec();
//        return newState;
//    }

//    public void oneStepWrapper() throws Exception{
//        PrgState prg = repo.getCurrentProgram();
//        oneStep(prg);
//    }

//    public PrgState getCrtState(){
//        return repo.getCurrentProgram();
//    }

    //    public void allStep() throws Exception {
//        PrgState prg = repo.getCurrentProgram();
//        repo.logPrgStateExec();
//        while(!prg.getExeStack().isEmpty()){
//            oneStep(prg);
//            displayState();
//
//            List<Integer> symTableAddresses = GarbageCollector.getAddrFromSymTable(prg.getSymTable().getContent().values());
//            Map<Integer, IValue> newHeapContent = GarbageCollector.safeGarbageCollector(symTableAddresses, prg.getHeap());
//            prg.getHeap().setContent(newHeapContent);
//
//            repo.logPrgStateExec();
//        }
//    }
}
