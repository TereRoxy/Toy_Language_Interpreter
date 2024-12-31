package view.GUI;

import controller.IController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.adt.*;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepo;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class InterpreterService {
    private final IController controller;
    private IStatement program;
    private Stage mainStage;

    public InterpreterService(IController controller, IStatement program, Stage mainStage) {
        this.controller = controller;
        this.program = program;
        this.mainStage = mainStage;
    }

    //selects the program based on the given key, creates/updates the necessary components and runs it
    public void run() {
        createProgram();
    }

    private void createProgram() {

        //call the type checker before creating the program state
        try{
            // if the program doesn't pass the type checker, an exception will be thrown
            program.typecheck(new MyDictionary<>());

            MyIStack<IStatement> stack = new MyStack<>();
            MyIDictionary<String, IValue> symTable = new MyDictionary<>();
            MyIList<IValue> out = new MyList<>();
            MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
            MyIHeap heap = new MyHeap();

            //create the new program state
            PrgState prgState = new PrgState(symTable, stack, out, program, fileTable, heap);
            List<PrgState> newRepoList = new ArrayList<>();
            newRepoList.add(prgState);
            IRepo repo = controller.getRepo();
            repo.setProgramList(newRepoList);
            controller.setRepo(repo);

        } catch (Exception e) {
            //handle the exception --> create a new alert window
            showErrorAlert(e.getMessage());
        }
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Type Check Error");
        alert.setHeaderText("An error occurred during type checking");
        alert.setContentText(errorMessage);
        alert.showAndWait();

        mainStage.close();
    }

}