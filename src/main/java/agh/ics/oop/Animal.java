package agh.ics.oop;

import java.util.Random;
import java.util.UUID;

public class Animal implements Comparable {

    final public UUID uuid;
    private IWorldMap worldMap;
    private Genotype genotype;
    MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;

    private int offspringCounter;
    private int grassEaten;
    private int dateOfBirth = -1;
    private int dateOfDeath = -1;

    Random rand = new Random();
    public Animal(IWorldMap worldMap, Vector2d pos) {
        if(worldMap == null)
            throw new NullPointerException("Null Pointer Exception during animal creation (worldMap is null)");
        if(pos == null)
            throw new NullPointerException("Null Pointer Exception during animal creation (pos is null)");

        this.worldMap = worldMap;
        this.pos = pos;
        this.uuid = UUID.randomUUID();
        this.dateOfBirth = worldMap.getWorldAge();
        this.energy = worldMap.getSimulationOptions().breedingCost() * 2;

        genotype = new Genotype(worldMap.getSimulationOptions());
    }
    public Animal(Animal parent1, Animal parent2) {
        if(parent1 == null || parent2 == null)
            throw new NullPointerException("Null Pointer Exception during animal creation (parent is null)");

        this.worldMap = parent1.worldMap;
        this.pos = parent1.pos;
        this.uuid = UUID.randomUUID();
        this.dateOfBirth = worldMap.getWorldAge();
        this.energy = worldMap.getSimulationOptions().breedingCost() * 2;

        if(this.rand.nextInt(0, 1) == 1){
            Animal tmp = parent1;
            parent1 = parent2;
            parent2 = tmp;
        }

        float energyRatio = (float) parent1.energy / (float) parent2.energy;
        genotype = new Genotype(parent1.genotype, parent2.genotype, energyRatio);

        parent1.childrenIsBorn();
        parent1.energy -= worldMap.getSimulationOptions().breedingCost();
        parent2.childrenIsBorn();
        parent2.energy -= worldMap.getSimulationOptions().breedingCost();

        this.orientation.rotate(Rotations.randomVal());
    }

    public Vector2d getPos(){ return this.pos; }

    public int getEnergy(){ return this.energy; }

    public void childrenIsBorn(){ offspringCounter++; };

    public void subtractEnergy(int val){
        this.energy -= val;
    }

    public void eatGrass(){
        grassEaten++;
        this.energy += worldMap.getSimulationOptions().singleGrassEnergy();
    }

    public void move(){
        this.orientation = this.orientation.rotate(this.genotype.nextGene());
        pos = this.worldMap.stepsAt(this, this.pos.add(this.orientation.toUnitVector()));
    }
    /**
     * Checks if animal should die, and handles its death if necessary.
     */
    public void meetGrimReaper() {
        if(this.energy <= 0) {
            dateOfDeath = worldMap.getWorldAge();
            worldMap.animalDies(this);
        }
    }
    @Override
    public int compareTo(Object otherObject) {
        if(otherObject == null)
            throw new NullPointerException("Null Pointer Exception during animals comparison");
        if(!(otherObject instanceof Animal))
            throw new IllegalArgumentException("Non-animal entity provided during animals comparison");

        Animal other = (Animal) otherObject;

        if(this.energy != other.energy)
            return this.energy - other.energy;
        else if (this.dateOfBirth != other.dateOfBirth)
            return other.dateOfBirth - this.dateOfBirth;
        else if (this.offspringCounter != other.offspringCounter)
            return this.offspringCounter - other.offspringCounter;
        else
            return this.uuid.compareTo(other.uuid);
        /*
        Using UUID, because TreeSet is broken, and it does not give a **** about Set interface contract and
        the fact that despite comparing two objects returns 0, they still may not be equal in terms of equals();
        In other words, this comparator must never return 0, because two identical animals could be treated as
        the same animal.

        Three hours of debugging.
        */
    }

    public String toString() {
        return "Pos: " + this.pos.toString() + ", Energy: " + this.energy +
                ", Geotype: " + this.genotype +
                ", UUID: " + this.uuid;
    }
    public String stats() {
        return "UUID: \t\t" + this.uuid +
                "\nPosition (x, y):\t" + this.pos.toString() +
                "\nOrientation:\t" + this.orientation.shortRepresentation() +
                "\nEnergy:\t\t" + this.energy +
                "\nEaten grass count:\t" + this.grassEaten +
                "\nChildren count:\t" + this.offspringCounter +
                "\nGenotype:\t\t" + this.genotype +
                "\nAlive since:\t" + this.dateOfBirth +
                "(" + (this.worldMap.getWorldAge() - this.dateOfBirth) + "frames ago)" +
                "\nDead since:\t" + (this.dateOfDeath == -1 ? "N/A" : (Integer.toString(this.dateOfDeath) +
                "(" + (this.worldMap.getWorldAge() - this.dateOfDeath) + "frames ago)"));
    }
    public String textureName(){ return "animal_" + this.orientation.shortRepresentation(); }

    public String objectDescription(){ return "A(" + this.energy + " energy)"; }
}