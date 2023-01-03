package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


public class SimulationUIApp {
    private GridPane grid;
    private final GuiElementBox[][] elementBoxes;
    private final Thread engineThread;
    private final SimulationEngine simulationEngine;
    private final IWorldMap worldMap;
    private final Stage stage;
    private final int mapSizeX;
    private final int mapSizeY;
    private final int simulationID;

    private Label statField;

    private List<GuiElementBox> usedBoxes = new ArrayList<>();
    final int CELLSIZE = 60;

    public SimulationUIApp(SimulationOptions simulationOptions, int simulationID){
        this.stage = new Stage();

        this.simulationEngine = new SimulationEngine(simulationOptions, this);
        this.worldMap = this.simulationEngine.getWorldMap();

        this.mapSizeX = simulationOptions.mapSizeX();
        this.mapSizeY = simulationOptions.mapSizeY();
        this.simulationID = simulationID;

        this.grid = new GridPane();

        this.elementBoxes = new GuiElementBox[this.mapSizeY][this.mapSizeX];
        for(SingleField field : worldMap.getAllFields()){
            this.elementBoxes[field.fieldPos.x][field.fieldPos.y] = new GuiElementBox(field);
        }

        Scene scene = new Scene(this.grid, CELLSIZE*(mapSizeX+8), CELLSIZE*(mapSizeY+3));
        this.stage.setScene(scene);
        this.stage.show();

        this.engineThread = new Thread(this.simulationEngine, "Simulation Thread " + simulationID);
        this.engineThread.start();
    }

    public void createMap() {
        grid.setPadding(new Insets(CELLSIZE, CELLSIZE, CELLSIZE, CELLSIZE));

        Label addedLabel = new Label("y/x");
        GridPane.setHalignment(addedLabel, HPos.RIGHT);
        GridPane.setValignment(addedLabel, VPos.BOTTOM);
        this.grid.add(addedLabel, 0, 0);
        this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));

        for (int x = 1; x < this.mapSizeX + 1; x++) {
            Label addedLabelX = new Label(Integer.toString(x));
            GridPane.setHalignment(addedLabelX, HPos.CENTER);
            GridPane.setValignment(addedLabelX, VPos.BOTTOM);
            this.grid.add(addedLabelX, x, 0);
            this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
        }
        for (int y = 1; y < this.mapSizeY + 1; y++) {
            Label addedLabelY = new Label(Integer.toString(y));
            GridPane.setHalignment(addedLabelY, HPos.RIGHT);
            GridPane.setValignment(addedLabelY, VPos.CENTER);
            this.grid.add(addedLabelY, 0, y);
            this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        }

        for(SingleField field : worldMap.getAllFields()){
            GuiElementBox box = this.elementBoxes[field.fieldPos.x][field.fieldPos.y];
            box.updateObject();

            this.grid.add(box.getGridElement(), box.getXCoordinate() + 1, this.mapSizeY - box.getYCoordinate());
            usedBoxes.add(box);
        }

        Button pauseButton = new Button("Pause");
        this.grid.add(pauseButton, this.mapSizeX + 2, 1);
        pauseButton.setOnAction((event) -> {
            synchronized (this.simulationEngine){
                this.simulationEngine.setPause(!this.simulationEngine.isPaused());
                this.simulationEngine.notify();
            }
        });

        Button nextFrameButton = new Button("Single Frame");
        this.grid.add(nextFrameButton, this.mapSizeX + 3, 1);
        nextFrameButton.setOnAction((event) -> {
            synchronized (this.simulationEngine){
                this.simulationEngine.setPause(true);
                this.simulationEngine.notify();
            }
        });

        this.statField = new Label("Stats:");
        this.grid.add(this.statField, this.mapSizeX + 2, 2, 2, 3);
        this.statField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillHeight(this.statField, true);

        this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE/2));
        this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
        this.grid.getColumnConstraints().add(new ColumnConstraints(4*CELLSIZE));

        this.grid.setGridLinesVisible(false);
    }
    public void updateGuiMap() {
        //Refreshing fields that were used in last frame
        for(GuiElementBox box : this.usedBoxes){
            box.updateObject();

            this.grid.getChildren().remove(box.getGridElement());
            this.grid.add(box.getGridElement(), box.getXCoordinate() + 1, this.mapSizeY - box.getYCoordinate());
        }

        this.usedBoxes.clear();

        //Refreshing fields used currently
        for(SingleField field : worldMap.getOccupiedFields()){
            GuiElementBox box = this.elementBoxes[field.fieldPos.x][field.fieldPos.y];
            box.updateObject();

            this.grid.getChildren().remove(box.getGridElement());
            this.grid.add(box.getGridElement(), box.getXCoordinate() + 1, this.mapSizeY - box.getYCoordinate());
            this.usedBoxes.add(box);
        }

        this.statField.setText("Stats:\n" + worldMap.stats());
    }
}