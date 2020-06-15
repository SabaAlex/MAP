package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ///Run Program Window
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("/RunPrograms.fxml"));
        Parent mainWindow = mainLoader.load();

        RunProgramsController runProgramsController = mainLoader.getController();

        primaryStage.setTitle("Run Programs Window");
        primaryStage.setScene(new Scene(mainWindow, 813.0, 460.0));
        primaryStage.show();


        ///Select Program Window
        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("/SelectPrograms.fxml"));

        Parent secondWindow = secondaryLoader.load();

        SelectProgramsController selectProgramsController = secondaryLoader.getController();
        selectProgramsController.setCtrl(runProgramsController);

        Stage secondaryStage = new Stage();

        secondaryStage.setTitle("Select Programs Window");
        secondaryStage.setScene(new Scene(secondWindow, 712, 404));
        secondaryStage.show();
    }

}
