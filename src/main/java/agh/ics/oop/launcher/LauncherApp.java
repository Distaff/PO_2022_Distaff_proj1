package agh.ics.oop.launcher;

import agh.ics.oop.SimulationOptions;
import agh.ics.oop.gui.SimulationUIApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class LauncherApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();

        SimulationOptions simulationOptions = SimulationOptions.GetOptionsFromFile("C:\\Users\\Distaff\\source\\Infa\\PO_2022_Distaff_proj_1\\options.txt");

        SimulationUIApp simApp = new SimulationUIApp(simulationOptions, 1);
        simApp.updateGuiMap1();

        System.out.println("Done.");
    }
}
