package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    @FXML
    private TextField numPrgStatesField;
    @FXML
    private TableView<HeapEntry> heapTableTView;
    @FXML
    private ListView<IValue> outLView;
    @FXML
    private ListView<StringValue> fileTableLView;
    @FXML
    private ListView<PrgState> prgStateLView;
    @FXML
    private TableView<SymTableEntry> symTableTView;
    @FXML
    private ListView<IStatement> exeStackLView;
    @FXML
    private Button runOneStepButton;
    @FXML
    private Button openLogFileButton;
    @FXML
    private Button runAllStepsButton;

    private SelectProgramWindow selectProgramWindow;
    private ExecutionService programExecutionService;
    private PrgState selectedProgram;

    public void setProgramExecutionService(ExecutionService programExecutionService) {
        this.programExecutionService = programExecutionService;
        selectedProgram = programExecutionService.getDefaultProgram();
    }

    public void setSelectProgramWindow(SelectProgramWindow newselectProgramWindow) {
        this.selectProgramWindow = newselectProgramWindow;
        selectProgramWindow.getProgramList().getSelectionModel().selectedItemProperty().addListener((a, b, ex) -> reload(ex));
    }

    @FXML
    public void initialize() {

        // Initialize table columns
        heapTableTView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("address"));
        heapTableTView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("value"));
        symTableTView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("variableName"));
        symTableTView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("value"));

        // Initialize the list view
        prgStateLView.setCellFactory(TextFieldListCell.forListView(new StringConverter<PrgState>() {
            @Override
            public String toString(PrgState prgState) {
                return "Program State " + prgState.getId();
            }

            @Override
            public PrgState fromString(String string) {
                return null; // Not needed for display purposes
            }
        }));

        // Set the event listener for the ListView PrgState selection
        prgStateLView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prgStateLView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedProgram = newValue;
                refreshPrgState(selectedProgram);
            }
        });


        //Connect the buttons to their handlers
        runOneStepButton.setOnAction(event -> runOneStepButtonHandler());
        runAllStepsButton.setOnAction(event -> runAllStepsButtonHandler());
        openLogFileButton.setOnAction(event -> openLogFile());
    }

    public void reload(IStatement selectedStatement) {
        //rebuild the execution service and the program state with the new selected statement
        try {
            ExecutionService service = new ExecutionService(programExecutionService.getController(), selectedStatement);
            setProgramExecutionService(service);
            refresh(selectedProgram);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void refresh(PrgState currentState) {
        // Clear all existing data
        clearAll();

        // Update the number of program states
        numPrgStatesField.setText(programExecutionService.getNumPrgStates().toString());

        // Update the heap table
        currentState.getHeap().getMap().forEach((address, value) ->
                heapTableTView.getItems().add(new HeapEntry(address, value.toString()))
        );

        // Update the output list view
        currentState.getOutput().getAll().forEach(output ->
                outLView.getItems().add(output)
        );

        // Update the file table list view
        currentState.getFileTable().getKeys().forEach(file ->
                fileTableLView.getItems().add(file)
        );

        // Update the program state list view
        programExecutionService.getPrograms().forEach(prgState ->
                prgStateLView.getItems().add(prgState)
        );

        // Update the symbol table
        currentState.getSymTable().getContent().forEach((variableName, value) ->
                symTableTView.getItems().add(new SymTableEntry(variableName, value.toString()))
        );

        // Update the execution stack list view
        currentState.getExeStack().getContent().forEach(statement ->
                exeStackLView.getItems().add(statement)
        );
    }

    private void refreshPrgState(PrgState state){
        // We only need to refresh the symbol table and the execution stack as the other components are shared
        symTableTView.getItems().clear();
        exeStackLView.getItems().clear();

        state.getSymTable().getContent().forEach((variableName, value) ->
                symTableTView.getItems().add(new SymTableEntry(variableName, value.toString()))
        );

        state.getExeStack().getContent().forEach( (statement) ->
                exeStackLView.getItems().add(statement)
        );
    }

    private void clearAll() {
        numPrgStatesField.setText("");
        heapTableTView.getItems().clear();
        outLView.getItems().clear();
        fileTableLView.getItems().clear();
        prgStateLView.getItems().clear();
        symTableTView.getItems().clear();
        exeStackLView.getItems().clear();
    }

    private void runOneStepButtonHandler() {
        try {
            programExecutionService.run();
            refresh(selectedProgram);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void runAllStepsButtonHandler() {
        try {
            programExecutionService.runAll();
            refresh(selectedProgram);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void openLogFile() {
        File logFile = new File("program-execution.log");
        if (logFile.exists()) {
            try {
                Desktop.getDesktop().open(logFile);
            } catch (IOException e) {
                showErrorAlert("An error occurred while opening the log file.");
            }
        } else {
            showErrorAlert("The log file does not exist.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}