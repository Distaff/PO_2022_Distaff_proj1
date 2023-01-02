package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    private SingleField[][] mapFields;
    private Set<Vector2d> occupiedFields = new HashSet<>(); //TODO: check every occupied field after eating phase
    protected SimulationOptions simulationOptions;
    private int worldAge;

    AbstractWorldMap(SimulationOptions simulationOptions){
        this.simulationOptions = simulationOptions;
        mapFields = new SingleField[simulationOptions.mapSizeX()][simulationOptions.mapSizeY()];
        for(int x = 0; x < simulationOptions.mapSizeX(); x++){
            for(int y = 0; y < simulationOptions.mapSizeY(); y++){
                mapFields[x][y] = new SingleField(this, new Vector2d(x, y));
            }
        }
    }

    @Override
    public SimulationOptions getSimulationOptions() { return simulationOptions; }
    @Override
    public int getWorldAge() { return worldAge; }
    @Override
    public boolean isOccupied(Vector2d position) { return this.occupiedFields.contains(position); }

    protected SingleField fieldAt(Vector2d pos) { return null; }

    /*
    @Override
    public void growGrass() {
        //TODO
    }

    @Override
    public void makeFieldsPrivelaged() {
        //TODO
    }
     */

    @Override
    public boolean place(Animal animal) {
        if(animal == null)
            throw new IllegalArgumentException("Animal cannot be null");
        if(!animal.getPos().precedes(new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY())) ||
            !animal.getPos().follows(new Vector2d(0, 0)))
        {
            throw new IllegalArgumentException("Animal cannot be placed on position: " + animal.getPos().toString());
        }

        fieldAt(animal.getPos()).pushAnimal(animal);
        this.occupiedFields.add(animal.getPos());
        return true;
    }

    @Override
    public abstract Vector2d stepsAt(Animal animal, Vector2d newPos);

    @Override
    public void animalDies(Animal animal) {
        fieldAt(animal.getPos()).popAnimal(animal);
        fieldAt(animal.getPos()).grimReaperHasCame();
    }

    protected void moveAnimal(Animal animal, Vector2d newPos){
        Vector2d oldPos = animal.getPos();

        fieldAt(oldPos).popAnimal(animal);
        if(fieldAt(oldPos).isEmpty())
            occupiedFields.remove(oldPos);

        fieldAt(newPos).pushAnimal(animal);
        occupiedFields.add(newPos);
    }


    @Override
    public Collection<SingleField> getOccupiedFields(){
        List<SingleField> fieldsList = new ArrayList<>();
        for (Vector2d fieldPos : this.occupiedFields) {
            fieldsList.add(this.fieldAt(fieldPos));
        }
        return fieldsList;
    }

    @Override
    public Collection<Animal> getAnimalsOnMap() {
        List<Animal> animalList = new ArrayList<>();
        for (Vector2d fieldPos : this.occupiedFields) {
            animalList.addAll(this.fieldAt(fieldPos).getAnimalsOnField());
        }
        return animalList;
    }

    @Override
    public String toString(){
        return this.drawEngine.draw(new Vector2d(0, 0),
            new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY()));
    }
}
