package view.console.commands;

import controller.IController;
import exception.CompletedProgramException;

public class ExecNextStep extends Command {
    private final IController controller;

    public ExecNextStep(Integer key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws Exception {
        if (controller.areProgramsFinished()) {
            throw new CompletedProgramException("All programs have finished executing");
        }
        try{
            controller.oneStepForAllPrg(controller.getRepo().getProgramList());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
