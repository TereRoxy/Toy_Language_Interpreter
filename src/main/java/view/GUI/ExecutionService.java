package view.GUI;

import controller.IController;
import exception.CompletedProgramException;
import model.state.PrgState;
import model.statements.IStatement;
import java.util.ArrayList;
import java.util.List;

public class ExecutionService {
    private final IController controller;
    private IStatement program;
    //private PrgState prgState;

    public ExecutionService(IController controller, IStatement program) throws Exception {
        this.controller = controller;
        this.program = program;
        ProgramCreator programCreator = new ProgramCreator(this.controller, this.program);
        programCreator.createProgram();
        //this.prgState = controller.getRepo().getProgramList().getFirst();
        controller.initializeExecutor();
    }

    //wrapper for the controller's oneStepForAll method
    public void run() throws Exception {
        if (controller.areProgramsFinished()) {
            controller.closeExecutor();
            throw new CompletedProgramException("All programs have finished executing");
        }
        controller.oneStepForAllPrg(controller.getRepo().getProgramList());
    }

    public void runAll() throws Exception {
        controller.allStep();
    }

    public List<PrgState> getPrograms() {
        return controller.getRepo().getProgramList();
    }

    public PrgState getDefaultProgram() {
        return controller.getRepo().getProgramList().getFirst();
    }

    public IController getController() {
        return controller;
    }

    public Integer getNumPrgStates() {
        return controller.getRepo().getSize();
    }
}