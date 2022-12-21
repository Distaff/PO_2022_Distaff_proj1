package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements Comparable {
    private IWorldMap worldMap;
    private Genotype genotype;
    MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;

    private int offspringCounter;
    private int grassEaten;
    private int dateOfBirth;
    private int dateOfDeath;

    Random rand = new Random();
    public Animal(IWorldMap worldMap) {
        genotype = new Genotype(worldMap.getSimulationOptions());
    }
    public Animal(Animal parent1, Animal parent2) {
        this.dateOfBirth = worldMap.getWorldAge();
        this.worldMap = parent1.worldMap;

        parent1.childrenIsBorn();
        parent2.childrenIsBorn();


        if(this.rand.nextInt(0, 1) == 1){
            Animal tmp = parent1;
            parent1 = parent2;
            parent2 = tmp;
        }
        genotype = new Genotype(parent1.genotype, parent2.genotype, (float) parent1.energy / (float) parent2.energy);

        //TODO: Subtract

        this.orientation.rotate(Rotations.randomVal());
    }

    public String toString() {
        return "Position (x, y): " + this.pos.toString() +
                ", Orientation: " + this.orientation.shortRepresentation();
    }

    public String textureName(){ return "animal_" + this.orientation.shortRepresentation(); }

    public String objectDescription(){ return "Animal: " + this.getPos().toString(); }


    public Vector2d getPos(){ return this.pos; }

    public void childrenIsBorn(){ offspringCounter++; };

    public void move(){
        this.orientation.rotate(this.genotype.nextGene());
        this.worldMap.stepsAt(this.pos.add(this.orientation.toUnitVector()));
    }

    @Override
    public int compareTo(Object otherObject) {
        if(otherObject == null)
            throw new NullPointerException("Null Pointer Exception thrown during animals comparison");

        Animal other = (Animal) otherObject;

        if(this.energy == other.energy){
            if(this.dateOfBirth == other.dateOfBirth){
                if(this.offspringCounter == other.offspringCounter){
                    return 0;
                } else return this.offspringCounter - other.offspringCounter;
            } else return other.dateOfBirth - this.dateOfBirth;
        } else return this.energy - other.energy;
    }
}