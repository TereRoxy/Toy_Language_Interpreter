package view.console.commands;

import controller.IController;
import model.statements.IStatement;
import view.console.menus.OneStepMenu;

import java.util.function.Supplier;

public class RunExample extends Command {

    private final IController controller;
    private boolean execOneStep;
    private final Supplier<IStatement> programSupplier = null;

    public RunExample(Integer key, String description, IController controller, boolean execOneStep) {
        super(key, description);
        this.controller = controller;
        this.execOneStep = execOneStep;
    }

    @Override
    public void execute() {
        try {
            if (execOneStep){
                controller.initializeExecutor();

                OneStepMenu nextInstruction = new OneStepMenu();
                nextInstruction.addCommand(new ExecNextStep(1, "Execute next step", controller));
                nextInstruction.addCommand(new ExitCommand(0, "Exit"));
                nextInstruction.show();

                controller.closeExecutor();
            }
            else{
                controller.allStep();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setExecOneStep(boolean execOneStep) {
        this.execOneStep = execOneStep;
    }

    public IController getController() {
        return controller;
    }
}
