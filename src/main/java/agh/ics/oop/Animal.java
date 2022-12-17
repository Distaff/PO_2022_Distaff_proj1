package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
    MapDirection orientation = MapDirection.NORTH;

    private Vector2d pos;
    private IWorldMap worldMap;
    private List<IPositionChangeObserver> observerList = new ArrayList<>();


    public Animal(IWorldMap argWorldMap, Vector2d initPos) {
        this.pos = initPos;
        this.worldMap = argWorldMap;
    }
    public Animal(IWorldMap argWorldMap) {
        this(argWorldMap, new Vector2d(0,0));
    }

    @Override
    public String toString() {
        return this.orientation.shortRepresentation();
    }

    public String longStringRepresentation(){ return "animal_" + this.orientation.toString(); }

    public String objectDescription(){ return "Animal: " + this.getPos().toString(); }

    public String debugInfo() {
        return "Position (x, y): " + this.pos.toString() +
                ", Orientation: " + this.orientation.toString();
    }

    public Vector2d getPos(){
        return this.pos;
    }
    public boolean isAt(Vector2d position) {
        return this.pos.equals(position);
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case LEFT: {
                this.orientation = this.orientation.prev();
            } break;
            case RIGHT: {
                this.orientation = this.orientation.next();
            } break;
            case FORWARD: {
                Vector2d newPos = this.pos.add(this.orientation.toUnitVector());
                if (worldMap.canMoveTo(newPos)) {
                    Vector2d oldPos = this.pos;
                    this.pos = newPos;
                    informObservers(oldPos, newPos);
                }
            } break;
            case BACKWARD: {
                Vector2d newPos = this.pos.add(this.orientation.toUnitVector().opposite());
                if(worldMap.canMoveTo(newPos)) {
                    Vector2d oldPos = this.pos;
                    this.pos = newPos;
                    informObservers(oldPos, newPos);
                }
            } break;
        }
    }

    public void addObserver(IPositionChangeObserver added){
        observerList.add(added);
    }

    public void removeObserver(IPositionChangeObserver removed){
        observerList.remove(removed);
    }
    private void informObservers(Vector2d oldPos, Vector2d newPos){
        for (IPositionChangeObserver i: observerList) {
            i.positionChanged(oldPos, newPos, this);
        }
    }
}
