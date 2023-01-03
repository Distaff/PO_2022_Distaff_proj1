package agh.ics.oop.launcher;

import agh.ics.oop.SimulationOptions;
import agh.ics.oop.gui.SimulationUIApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LauncherApp extends Application {
    private int nextID = 0;

    @Override
    public void init() throws Exception {


        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setHgap(10);


        final TextField path = new TextField();
        path.setPromptText("Enter path to simulation settings file");
        path.setPrefColumnCount(30);
        //path.getText();
        grid.add(path, 0, 0);

        Button browse = new Button("Browse");
        grid.add(browse, 1, 0);

        Button launch = new Button("Launch");
        grid.add(launch, 2, 0);



        //TEST
        String backgroundPath = "src/main/rescources/animal_N.png";
        Image backgroundImg = null;
        ImageView backgroundImgView;
        try {
            backgroundImg = new Image(new FileInputStream(backgroundPath));
        } catch (FileNotFoundException ex) {
            System.err.println("Exception caught while loading textures: " + ex);
            System.exit(-1);
        }
        backgroundImgView = new ImageView(backgroundImg);
        backgroundImgView.setFitWidth(40);
        backgroundImgView.setFitHeight(40);
        // TEST

        grid.add(backgroundImgView, 0, 1);

        Scene scene = new Scene(grid, 600, 100);
        primaryStage.setScene(scene);

        spawnSimulation(new File("C:\\Users\\Distaff\\source\\Infa\\PO_2022_Distaff_proj_1\\options.txt"));
        primaryStage.show();
        System.out.println("Done.");
    }

    private void spawnSimulation(File file){
        SimulationOptions simulationOptions = SimulationOptions.GetOptionsFromFile(file);

        SimulationUIApp simApp = new SimulationUIApp(simulationOptions, nextID++);
        simApp.updateGuiMap1(); //TODO
    }
}
