package view.GUI;

import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.statements.IStatement;
import model.statements.NopStatement;
import repository.IRepo;
import repository.Repo;

import java.io.IOException;

public class InterpreterApplication extends Application {
    public static final String SELECT_PROGRAM_VIEW = "select-program-view.fxml";
    public static final String PROGRAM_EXECUTION_VIEW = "program-execution-view.fxml";
    public static final String LOG_FILE = "program-execution.log";

    @Override
    public void start(Stage stage) throws IOException {

        //start program with select program window
        ProgramRepo examplesRepo = new ProgramRepo();

        FXMLLoader fxmlLoader = new FXMLLoader(InterpreterApplication.class.getResource(SELECT_PROGRAM_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Get the controller and set the ProgramRepo
        SelectProgramWindow selectProgram = fxmlLoader.getController();
        selectProgram.setProgramRepo(examplesRepo);

        stage.setTitle("Interpreter");
        stage.setScene(scene);
        stage.show();

        IStatement nop = new NopStatement();
        //create new window for program execution
        IRepo repo = new Repo(null, LOG_FILE);
        // Create the Controller with the shared repository
        IController controller = new Controller(repo);

        try {
            ExecutionService service = new ExecutionService(controller, nop);
            FXMLLoader fxmlLoader2 = new FXMLLoader(InterpreterApplication.class.getResource(PROGRAM_EXECUTION_VIEW));
            Scene scene2 = new Scene(fxmlLoader2.load(), 800, 600);
            MainWindow mainWindow = fxmlLoader2.getController();
            mainWindow.setSelectProgramWindow(selectProgram);
            mainWindow.setProgramExecutionService(service);

            Stage executionStage = new Stage();
            executionStage.setTitle("Program Execution");
            executionStage.setScene(scene2);
            executionStage.show();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}