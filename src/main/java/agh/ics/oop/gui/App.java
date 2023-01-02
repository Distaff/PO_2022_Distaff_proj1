package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


public class App implements Runnable{
    private GridPane grid;
    private List<GuiElementBox> entityList = new ArrayList<>();
    private final SimulationEngine simulationEngine;
    private final IWorldMap worldMap;

    final int CELLSIZE = 40;


    public App(SimulationEngine simulationEngine, IWorldMap worldMap){
        this.simulationEngine = simulationEngine;
        this.worldMap = worldMap;
    }

    public void start(Stage primaryStage) {

        createGuiMap();
        updateGuiMap();

        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void createGuiMap() {
        int rowCount = map.getUpperRight().y - map.getLowerLeft().y;
        int columnCount = map.getUpperRight().x - map.getLowerLeft().x;

        grid = new GridPane();

        for(IMapElement i : map.getEntitiesOnMap()){
            entityList.add(new GuiElementBox(i));
        }
    }

    private void updateGuiMap() {
        grid.getChildren().clear();
        int rowCount = map.getUpperRight().y - map.getLowerLeft().y + 1;
        int columnCount = map.getUpperRight().x - map.getLowerLeft().x + 1;

        for(GuiElementBox i : entityList){
            i.updateObject();
            grid.add(i.getGridElement(), i.getGuiPosX(map.getLowerLeft().x), i.getGuiPosY(map.getUpperRight().y));
        }

        grid.add(new Label("y/x"), 0, 0);
        grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));

        for (int x = 1; x < columnCount + 1; x++) {
            grid.add(new Label(Integer.toString(x)), x, 0);
            grid.getColumnConstraints().add(new ColumnConstraints(CELLSIZE));
        }
        for (int y = 1; y < rowCount + 1; y++) {
            grid.add(new Label(Integer.toString(y)), 0, y);
            grid.getRowConstraints().add(new RowConstraints(CELLSIZE));
        }

        grid.setGridLinesVisible(true);
    }
}