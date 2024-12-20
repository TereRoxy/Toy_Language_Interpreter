package view.commands;

import controller.IController;
import exception.CompletedProgramException;

public class ExecNextStep extends Command {
    private IController controller;

    public ExecNextStep(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.oneStepForAllPrg(controller.getRepo().getProgramList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
