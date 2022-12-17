package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements IMapElement {
    private SimulationOptions simulationOptions;
    private IWorldMap worldMap;
    private List<IPositionChangeObserver> observerList = new ArrayList<>();
    MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;

    private int[] genes;
    private int lastActivatedGene;

    private int offspringCounter;
    private int grassEaten;
    private int dateOfBirth;
    private int dateOfDeath;

    static int


    public Animal(Animal parent1, Animal parent2) {
        ThreadLocalRandom rand = new ThreadLocalRandom();
        if(rand.nextInt(2) == 1){
            Animal tmp = parent1;
            parent1 = parent2;
            parent2 = tmp;
        }
        int crossPoint = simulationOptions.genotypeSize * (parent1.energy / parent2.energy);
        java.lang.System.arraycopy(parent1, 0, this.genes, 0, crossPoint);
        java.lang.System.arraycopy(parent2, crossPoint, this.genes, 0, simulationOptions.genotypeSize);

        int toMutate = rand.nextInt(simulationOptions.genotypeSize + 1);
        for(int i = 0; i < toMutate; i++){
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
