package view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterApplication extends Application {
    public static final String SELECT_PROGRAM_VIEW = "select-program-view.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        ProgramRepo examplesRepo = new ProgramRepo();

        FXMLLoader fxmlLoader = new FXMLLoader(InterpreterApplication.class.getResource(SELECT_PROGRAM_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Get the controller and set the ProgramRepo
        SelectProgramController controller = fxmlLoader.getController();
        controller.setProgramRepo(examplesRepo);

        stage.setTitle("Interpreter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}