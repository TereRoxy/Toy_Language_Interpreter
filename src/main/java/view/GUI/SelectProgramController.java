package view.GUI;

import controller.Controller;
import controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.statements.IStatement;
import repository.IRepo;
import repository.Repo;

import java.io.IOException;


public class SelectProgramController {
    public static final String PROGRAM_EXECUTION_VIEW = "program-execution-view.fxml";
    public static final String LOG_FILE = "program-execution.log";

    @FXML
    private ListView<String> programList;
    private ProgramRepo programDictionary;

    public void setProgramRepo(ProgramRepo programDictionary) {
        this.programDictionary = programDictionary;
        // Populate the ListView with the string representations of the IStatement objects
        programDictionary.values().forEach(statement -> programList.getItems().add(statement.toString()));

    }

    @FXML
    public void initialize() {
        // Set the event listener for the ListView selection
        programList.setOnMouseClicked(this::onProgramSelected);
    }

    public void onProgramSelected(MouseEvent event) {
        // Get the selected IStatement object from the dictionary
        IStatement selectedStatement = programDictionary.get(programList.getSelectionModel().getSelectedIndex() + 1);
        if (selectedStatement != null) {
            // Shared repository and controller for all program states
            IRepo repo = new Repo(null, LOG_FILE);
            // Create the Controller with the shared repository
            IController controller = new Controller(repo);

            // Load the execution view
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PROGRAM_EXECUTION_VIEW));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);

                Stage currentStage = (Stage) programList.getScene().getWindow();
                currentStage.close();

                // Create a new stage for the execution view
                Stage executionStage = new Stage();
                executionStage.setTitle("Program Execution");
                executionStage.setScene(scene);
                executionStage.show();

                // Create the InterpreterService with the selected IStatement
                InterpreterService service = new InterpreterService(controller, selectedStatement, executionStage);

                // Run the selected program
                service.run();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}