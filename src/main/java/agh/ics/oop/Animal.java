package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
    private IWorldMap worldMap;
    private List<IPositionChangeObserver> observerList = new ArrayList<>();
    MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;

    private int[] genes;
    private int lastActivatedGene;

    private int offspringCounter;
    private int grassEaten;
    private final int dateOfBirth;
    private final int dateOfDeath;



    public Animal(IWorldMap argWorldMap, Vector2d initPos) {
        this.pos = initPos;
        this.worldMap = argWorldMap;
    }

    @Override
    public String toString() {
        return "Position (x, y): " + this.pos.toString() +
                ", Orientation: " + this.orientation.shortRepresentation();
    }

    public String textureName(){ return "animal_" + this.orientation.shortRepresentation(); }

    public String objectDescription(){ return "Animal: " + this.getPos().toString(); }


    public Vector2d getPos(){
        return this.pos;
    }

    public void move(){

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
