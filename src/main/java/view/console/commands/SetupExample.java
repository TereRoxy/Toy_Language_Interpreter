package view.console.commands;

import controller.IController;
import model.adt.*;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepo;
import view.console.menus.TextMenu;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SetupExample extends Command {
    private final Supplier<IStatement> programSupplier;
    private final IController controller;

    public SetupExample(Integer key, String description, Supplier<IStatement> programSupplier, IController controller) {
        super(key, description);
        this.programSupplier = programSupplier;
        this.controller = controller;
    }

    @Override
    public void execute() {

        boolean passedTypeCheck = updateController();

        if (passedTypeCheck){
            TextMenu subMenu = new TextMenu();
            subMenu.addCommand(new RunExample(1, "Execute one step", controller, true));
            subMenu.addCommand(new RunExample(2, "Execute all steps", controller, false));
            subMenu.addCommand(new Command(0, "Back") {
                @Override
                public void execute() {
                }
            });
            subMenu.show();
        }
    }

    private boolean updateController() {
        IStatement program = this.programSupplier.get();

        //call the type checker before creating the program state
        try{
            // if the program doesn't pass the type checker, an exception will be thrown
             program.typecheck(new MyDictionary<>());

            PrgState prgState = inputProgram(program);
            List<PrgState> newRepoList = new ArrayList<>();
            newRepoList.add(prgState);
            IRepo repo = controller.getRepo();
            repo.setProgramList(newRepoList);
            controller.setRepo(repo);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Wrapper for creating a new program state
    private static PrgState inputProgram(IStatement ex) {
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        return new PrgState(symTable, stack, out, ex, fileTable, heap);
    }
}