package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    protected HashMap<Vector2d, IMapElement> entityMap = new HashMap<>();

    @Override
    public boolean isOccupied(Vector2d position) {
        return (objectAt(position) != null);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.entityMap.get(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPos()))
            throw new IllegalArgumentException("Animal cannot be placed on position: " + animal.getPos().toString());
        this.entityMap.put(animal.getPos(), animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos, IMapElement objectRef){
        IMapElement tmp = this.entityMap.remove(oldPos);
        if(tmp == null) throw new IllegalArgumentException("Changed position from " + oldPos + " to " + newPos +
                " but " + oldPos + "was not occupied!");
        this.entityMap.put(newPos, tmp);
        return;
    }

    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

    public List<IMapElement> getEntitiesOnMap(){
        return new ArrayList<IMapElement>(entityMap.values());
    }
    public String toString(){
        return this.drawEngine.draw(getLowerLeft(), getUpperRight());
    }
}
