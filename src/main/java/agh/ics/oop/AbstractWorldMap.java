package agh.ics.oop;  // przydałby się podział na pakiety

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IWorldMap {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    private SingleField[][] mapFields;
    private Set<Vector2d> occupiedFields = new HashSet<>();
    protected SimulationOptions simulationOptions;  // modyfikator dostępu; może gdyby to było final?
    private int worldAge;

    AbstractWorldMap(SimulationOptions simulationOptions) {
        this.simulationOptions = simulationOptions;
        mapFields = new SingleField[simulationOptions.mapSizeX()][simulationOptions.mapSizeY()];
        for (int x = 0; x < simulationOptions.mapSizeX(); x++) {
            for (int y = 0; y < simulationOptions.mapSizeY(); y++) {
                mapFields[x][y] = new SingleField(this, new Vector2d(x, y));
            }
        }
    }

    @Override
    public SimulationOptions getSimulationOptions() {
        return simulationOptions;
    }

    @Override
    public int getWorldAge() {
        return worldAge;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.occupiedFields.contains(position);
    }

    protected SingleField fieldAt(Vector2d pos) {
        return mapFields[pos.x][pos.y];
    }

    @Override
    public List<SingleField> getAllFields() {
        List<SingleField> fieldsList = new ArrayList<>();
        for (SingleField[] column : this.mapFields) {
            fieldsList.addAll(Arrays.stream(column).toList());
        }
        return fieldsList;
    }

    @Override
    public List<SingleField> getOccupiedFields() {
        List<SingleField> fieldsList = new ArrayList<>();
        for (Vector2d fieldPos : this.occupiedFields) {
            fieldsList.add(this.fieldAt(fieldPos));
        }
        return fieldsList;
    }

    @Override
    public List<Animal> getAnimalsOnMap() {
        List<Animal> animalList = new ArrayList<>();
        for (Vector2d fieldPos : this.occupiedFields) {
            animalList.addAll(this.fieldAt(fieldPos).getAnimalsOnField());
        }
        return animalList;
    }

    @Override
    public void grassHasGrown(Vector2d pos) {
        this.occupiedFields.add(pos);
    }

    @Override
    public abstract Vector2d stepsAt(Animal animal, Vector2d newPos);

    @Override
    public void animalDies(Animal animal) {
        fieldAt(animal.getPos()).popAnimal(animal);
        fieldAt(animal.getPos()).grimReaperHasCame();
    }

    @Override
    public boolean place(Animal animal) {
        if (animal == null)
            throw new IllegalArgumentException("Animal cannot be null");
        if (!animal.getPos().precedes(new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY())) ||
                !animal.getPos().follows(new Vector2d(0, 0))) {
            throw new IllegalArgumentException("Animal cannot be placed on position: " + animal.getPos().toString());
        }

        fieldAt(animal.getPos()).pushAnimal(animal);
        this.occupiedFields.add(animal.getPos());
        return true;
    }

    protected void moveAnimal(Animal animal, Vector2d newPos) {
        Vector2d oldPos = animal.getPos();

        fieldAt(oldPos).popAnimal(animal);
        if (fieldAt(oldPos).isEmpty())
            occupiedFields.remove(oldPos);

        animal.subtractEnergy(1);
        fieldAt(newPos).pushAnimal(animal);
        occupiedFields.add(newPos);
    }

    @Override
    public String toString() {
        return this.drawEngine.draw(new Vector2d(0, 0),
                new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY()));
    }

    @Override
    public String stats() {
        List<Animal> animalsOnMap = getAnimalsOnMap();

        int animalCount = animalsOnMap.size();

        int grassCount = (int) occupiedFields.stream()
                .map(this::fieldAt)
                .map(SingleField::grassPresent)
                .filter(b -> b == true).count();

        int emptyFields = simulationOptions.mapSizeX() * simulationOptions.mapSizeY() - getOccupiedFields().size();

        String mostPopularGenotype = animalsOnMap.stream()
                .map(Animal::genotypeDescription)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(IllegalStateException::new)
                .getKey();

        float avgEnergy = (float) animalsOnMap.stream()
                .map(Animal::getEnergy)
                .mapToDouble(a -> a)
                .average()
                .orElseThrow(IllegalStateException::new);

        return "Animals count:\t" + animalCount +
                "\nGrass count:\t" + grassCount +
                "\nEmpty fields:\t" + emptyFields +
                "\nMost popular genotype:\t" + mostPopularGenotype +
                "\nAverage energy:\t" + avgEnergy;
    }
}
