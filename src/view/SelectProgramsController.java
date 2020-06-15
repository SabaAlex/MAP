package view;

import ctrl.Controller;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.expressions.*;
import model.operators.ArithOp;
import model.operators.RelOp;
import model.state.PrgState;
import model.statements.*;
import model.symTable.MyDictionary;
import model.symTable.MyIDictionary;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repo.IRepo;
import repo.Repository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectProgramsController implements Initializable {

    private List<IStmt> prgStatements;
    private RunProgramsController RunCtrl;

    @FXML
    private ListView<String> programList;

    @FXML
    private Button executeButton;

    void setCtrl(RunProgramsController ctrl){
        this.RunCtrl = ctrl;
    }

    private void initPrgStatements(){
        ///1.
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        ///2.
        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(ArithOp.PLUS,new ValueExp(new IntValue(2)), new ArithExp(ArithOp.MULTIPLY,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(ArithOp.PLUS,new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));


        ///3.
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));


        ///4.
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        ///5.
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(40))),
                                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));


        ///6.
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(ArithOp.MINUS, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));


        ///7.
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("a"))),
                                                        new PrintStmt(new VarExp("v"))))))));

        ///8.
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new StringType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));


        prgStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8));
    }

    private List<String> getPrgAsStrings(){
        return prgStatements.stream().map(Object::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPrgStatements();
        List<String> prgStrings = getPrgAsStrings();

        ObservableList<String> strings = FXCollections.observableList(prgStrings);
        programList.setItems(strings);

        executeButton.setOnAction(actionEvent -> {
            int index = programList.getSelectionModel().getSelectedIndex();

            if(index < 0)
                return;

            PrgState initialPrgState = new PrgState(prgStatements.get(index));

            IRepo repository;

            try {
                index+=1;

                MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();

                initialPrgState.getOriginalProgram().typeCheck(typeEnvironment);

                repository = new Repository(initialPrgState);
                repository.setlogFilePath("log" + index + ".txt");

                Controller ctrl = new Controller(repository);

                this.RunCtrl.setController(ctrl);
            }
            catch(MyException exception)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
                alert.showAndWait();
                return;
            }

        });
    }
}
