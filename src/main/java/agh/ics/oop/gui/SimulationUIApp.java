package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SimulationUIApp {
    private GridPane grid;
    private GuiElementBox[][] mapEntities;
    private final Thread engineThread;
    private final SimulationEngine simulationEngine;
    private final IWorldMap worldMap;
    private final Stage stage;
    private final int mapSizeX;
    private final int mapSizeY;
    private final int simulationID;
    final int CELLSIZE = 40;

    public SimulationUIApp(SimulationOptions simulationOptions, int simulationID){
        this.stage = new Stage();

        this.simulationEngine = new SimulationEngine(simulationOptions, this);
        this.worldMap = this.simulationEngine.getWorldMap();

        this.mapSizeX = simulationOptions.mapSizeX();
        this.mapSizeY = simulationOptions.mapSizeY();
        this.simulationID = simulationID;

        this.grid = new GridPane();

        this.mapEntities = new GuiElementBox[this.mapSizeY][this.mapSizeX];
        for(SingleField field : worldMap.getAllFields()){
            this.mapEntities[field.fieldPos.x][field.fieldPos.y] = new GuiElementBox(field);
        }

        Scene scene = new Scene(this.grid, 600, 600);
        this.stage.setScene(scene);
        this.stage.show();

        this.engineThread = new Thread(this.simulationEngine, "Simulation Thread " + simulationID);
        this.engineThread.start();
    }

    public void updateGuiMap1() {
        System.out.println("ping");
        grid.setPadding(new Insets(10, 10, 10, 10));

        this.grid.add(new Label("y/x"), 0, 0);
        this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));

        for (int x = 1; x < this.mapSizeX + 1; x++) {
            this.grid.add(new Label(Integer.toString(x)), x, 0);
            this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
        }
        for (int y = 1; y < this.mapSizeY + 1; y++) {
            this.grid.add(new Label(Integer.toString(y)), 0, y);
            this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        }


        this.grid.setGridLinesVisible(true);
    }
    public void updateGuiMap() {
        List<VBox> c = new ArrayList<>();
        for (GuiElementBox[] column : mapEntities) {
            for (GuiElementBox entity : column) {
                c.add(entity.getGridElement());
            }
        }
        this.grid.getChildren().removeAll(c);

        for(SingleField field : worldMap.getAllFields()){
            GuiElementBox i = this.mapEntities[field.fieldPos.x][field.fieldPos.y];
            i.updateObject();
            this.grid.add(i.getGridElement(), field.fieldPos.x + 1, this.mapSizeY - field.fieldPos.y - 1);
            //GridPane.setFillWidth(label, true);
            //this.grid.add(i.label, field.fieldPos.x + 1, this.mapSizeY - field.fieldPos.y + 1);
            //this.grid.add(i.backgroundImgView, field.fieldPos.x + 1, this.mapSizeY - field.fieldPos.y + 1);
            //this.grid.add(i.imgView, field.fieldPos.x + 1, this.mapSizeY - field.fieldPos.y + 1);
        }

        /*
        this.grid.add(new Label("y/x"), 0, 0);
        this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
*/
        /*
        for (int x = 1; x < this.mapSizeX + 1; x++) {
            this.grid.add(new Label(Integer.toString(x)), x, 0);
            this.grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
        }
        for (int y = 1; y < this.mapSizeY + 1; y++) {
            this.grid.add(new Label(Integer.toString(y)), 0, y);
            this.grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        }
         */


        this.grid.setGridLinesVisible(true);
    }
}