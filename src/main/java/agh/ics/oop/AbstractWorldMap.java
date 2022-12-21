package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    private int worldAge;

    @Override
    public boolean isOccupied(Vector2d position) {
        return (objectAt(position) != null);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }   //TODO

    @Override
    public boolean place(Animal animal) {
        if(false)   //TODO: check if not out of bounds
            throw new IllegalArgumentException("Entity cannot be placed on position: " + animal.getPos().toString());
        //TODO
    }

    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

    public List<Animal> getEntitiesOnMap(){
        return null;
    }   //TODO
    public String toString(){
        return this.drawEngine.draw(getLowerLeft(), getUpperRight());
    }
}
