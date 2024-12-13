package view.commands;

import controller.IController;

public class RunExample extends Command {

    private IController controller;
    private boolean execOneStep;
    private Runnable run;

    public RunExample(String key, String description, IController controller, boolean execOneStep) {
        super(key, description);
        this.controller = controller;
        this.execOneStep = execOneStep;
    }

    public RunExample(String key, String description, Runnable run, IController controller, boolean execOneStep){
        super(key, description);
        this.execOneStep = execOneStep;
        this.run = run;
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            if (run != null) {
                run.run();
            }
            if (execOneStep){
                controller.oneStepForAllPrg(controller.getRepo().getProgramList());
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
