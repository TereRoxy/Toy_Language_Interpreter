package view.GUI;

import controller.Controller;
import controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.statements.IStatement;
import repository.IRepo;
import repository.Repo;

import java.io.IOException;


public class SelectProgramWindow {
    @FXML
    private ListView<IStatement> programList;
    private ProgramRepo programDictionary;

    public void setProgramRepo(ProgramRepo programDictionary) {
        this.programDictionary = programDictionary;
        // Populate the ListView with the string representations of the IStatement objects
        programDictionary.values().forEach(statement -> programList.getItems().add(statement));

    }

    @FXML
    public void initialize() {
    }

    public ListView<IStatement> getProgramList(){
        return programList;
    }
}