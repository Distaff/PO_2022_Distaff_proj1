package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class App extends Application{
    private IEngine engine;
    private IWorldMap map;
    GridPane grid;
    private List<GuiElementBox> entityList = new ArrayList<>();

    final int CELLSIZE = 40;
    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        MoveDirection[] directions = null;  //TODO
        map = null; //TODO
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        engine = new SimulationEngine(directions, map, positions);

        super.init();
    }
    public void start(Stage primaryStage){
        engine.run();

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