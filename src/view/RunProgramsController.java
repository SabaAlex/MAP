package view;

import ctrl.Controller;
import exceptions.MyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.exeStack.MyIStack;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.statements.IStmt;
import model.symTable.MyIDictionary;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RunProgramsController implements Initializable {

    private Controller ctrl;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValueColumn;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symTblTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symTblVarColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symTblValColumn;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<Integer> prgStateListView;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private Label noPrgStates;

    @FXML
    private Button executeOneStepButton;

    @FXML
    private Button resetProgramButton;

    void setController(Controller ctrl){
        this.ctrl = ctrl;
        populatePrgmStateId();
    }

    void populatePrgmStateId(){
        List<PrgState> prgStates = ctrl.getRepo().getPrgList();
        prgStateListView.setItems(FXCollections.observableList(getPrgStateIds(prgStates)));

        noPrgStates.setText("" + prgStates.size());
    }

    List<Integer> getPrgStateIds(List<PrgState> prgStateList) {
        return prgStateList.stream().map(PrgState::getPrgId).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        symTblVarColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symTblValColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        prgStateListView.setOnMouseClicked(MouseEvent -> changeProgramState(getCurrentProgramState()));

        resetProgramButton.setOnAction(ActionEvent -> resetProgram());

        executeOneStepButton.setOnAction(ActionEvent -> executeOneStep());
    }

    private void changeProgramState(PrgState currPrgState) {
        if(currPrgState == null)
        {
            return;
        }
        else {

            populateExecutionStack(currPrgState);
            populateSymbolTable(currPrgState);
            populateOutput(currPrgState);
            populateFileTable(currPrgState);
            populateHeapTable(currPrgState);
        }
    }

    void resetProgram(){
        ctrl.resetProgram();

        populatePrgmStateId();
        changeProgramState(getCurrentProgramState());
    }

    void executeOneStep() {

        if(ctrl == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean programStateLeft = ctrl.getRepo().getPrgList().isEmpty();

        if(programStateLeft){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            ctrl.oneStep();
        }
        catch(MyException exception)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        changeProgramState(getCurrentProgramState());
        ctrl.removeAfterOneStep();
        populatePrgmStateId();

    }

     PrgState getCurrentProgramState(){
        if(prgStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int id = prgStateListView.getSelectionModel().getSelectedItem();
        return ctrl.getRepo().getPrgStateWithId(id);
    }

     void populateHeapTable(PrgState currentPrgState) {
        MyIHeap<Integer, Value> heapTable = currentPrgState.getHeap();

        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>(heapTable.getContainer().entrySet());

        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

     void populateFileTable(PrgState currentPrgState) {

        MyIDictionary<StringValue, BufferedReader> fileTable = currentPrgState.getFileTable();
        List<String> fileList = new ArrayList<>();

        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getContainer().entrySet())
            fileList.add(entry.getKey().getVal());

        ObservableList<String> files = FXCollections.observableArrayList(fileList);

        fileListView.setItems(files);
        fileListView.refresh();
    }

     void populateOutput(PrgState currentPrgState) {
        ObservableList<Value> output = FXCollections.observableArrayList(currentPrgState.getOut().getContainer());

        outListView.setItems(output);
        outListView.refresh();
    }

     void populateSymbolTable(PrgState currentPrgState) {
        MyIDictionary<String, Value> symbolTable = currentPrgState.getSymTable();

        List<Map.Entry<String, Value>> symbolTableList = new ArrayList<>(symbolTable.getContainer().entrySet());
        symTblTableView.setItems(FXCollections.observableList(symbolTableList));
         symTblTableView.refresh();
    }

     void populateExecutionStack(PrgState currentPrgState) {
        MyIStack<IStmt> executionStack = currentPrgState.getExeStack();

        List<String> exeStackList = new ArrayList<>();

        for(IStmt s : executionStack.getContainer()){
            exeStackList.add(0,s.toString());
        }

        exeStackListView.setItems(FXCollections.observableList(exeStackList));
        exeStackListView.refresh();
    }
}
