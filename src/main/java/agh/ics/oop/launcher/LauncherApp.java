package agh.ics.oop.launcher;

import agh.ics.oop.SimulationOptions;
import agh.ics.oop.gui.SimulationUIApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LauncherApp extends Application {
    private int nextID = 0;
    private Stage launcherStage;

    public String chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showOpenDialog(this.launcherStage);
        if(file != null)
            return file.getAbsolutePath();
        else
            return "";
    }

    @Override
    public void start(Stage stage) {
        this.launcherStage = stage;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setHgap(10);

        final TextField path = new TextField();
        path.setPromptText("Enter path to simulation settings file");
        path.setText("options.txt");
        path.setPrefColumnCount(30);
        grid.add(path, 0, 0);

        Button browse = new Button("Browse");
        grid.add(browse, 1, 0);
        browse.setOnAction((event) -> {
            path.setText(chooseFile());
        });

        Button launch = new Button("Launch");
        grid.add(launch, 2, 0);
        launch.setOnAction((event) -> {
            spawnSimulation(path.getText());
        });

        Scene scene = new Scene(grid, 600, 100);
        this.launcherStage.setScene(scene);

        this.launcherStage.show();
        System.out.println("Done.");
    }

    private void spawnSimulation(String filepath){
        SimulationOptions simulationOptions = SimulationOptions.GetOptionsFromFile(filepath);

        SimulationUIApp simApp = new SimulationUIApp(simulationOptions, nextID++);
        simApp.createMap();
    }
}
