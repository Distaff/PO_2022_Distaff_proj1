package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    private SingleField[][] mapFields;
    private Set<Vector2d> occupiedFields = new HashSet<>();
    private SimulationOptions simulationOptions;
    private int worldAge;

    @Override
    public boolean isOccupied(Vector2d position) { return false; }

    @Override
    public boolean place(Animal animal) {
        if(false)   //TODO: check if not out of bounds
            throw new IllegalArgumentException("Entity cannot be placed on position: " + animal.getPos().toString());
        //TODO
    }

    public Vector2d stepsAt() {

    }

    public SingleField fieldAt(Vector2d pos) { return null; }

    public List<SingleField> getOccupiedFields(){
        List<SingleField> fieldsList = new ArrayList<>();
        for (Vector2d fieldPos : occupiedFields) {
            fieldsList.add(this.fieldAt(fieldPos));
        }
        return fieldsList;
    }

    public String toString(){
        return this.drawEngine.draw(new Vector2d(0, 0),
            new Vector2d(simulationOptions.mapSizeX(), simulationOptions.mapSizeY()));
    }
}
