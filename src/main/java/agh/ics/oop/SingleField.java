package agh.ics.oop;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class SingleField {

    public final IWorldMap worldMap;
    public final Vector2d fieldPos;
    private final int grassPriority; // co to jest?

    private TreeSet<Animal> animalList = new TreeSet<>(Animal::compareTo);
    private boolean grassPresent;
    private int deathCount = 0;

    public SingleField(IWorldMap worldMap, Vector2d fieldPos) {
        this.worldMap = worldMap;
        this.fieldPos = fieldPos;
        Random rand = new Random();
        this.grassPriority = rand.nextInt();
    }

    /**
     * Subscriber function called by World Map to inform a field
     * that it has been visited by Grim Reaper (animal died here)
     */
    public void grimReaperHasCame() {
        this.deathCount++;
    }

    public int getDeathCount() {
        return this.deathCount;
    }

    public List<Animal> getAnimalsOnField() {
        return this.animalList.stream().toList();
    }

    public void pushAnimal(Animal animal) {
        this.animalList.add(animal);
    }

    public void popAnimal(Animal animal) {
        this.animalList.remove(animal);
    }

    public void growGrass() {
        this.grassPresent = true;
    }

    public boolean grassPresent() {
        return this.grassPresent;
    }

    public boolean isEmpty() {
        return this.animalList.isEmpty() || this.grassPresent;
    }

    public void eatAndBreed() {
        Animal strongest = this.animalList.pollLast();
        Animal bride = this.animalList.pollLast();

        if (strongest != null && this.grassPresent) {
            this.grassPresent = false;
            strongest.eatGrass();
        }
        if (strongest != null && bride != null && bride.getEnergy() > worldMap.getSimulationOptions().minBreedingEnergy()) {
            this.animalList.add(new Animal(strongest, bride));
        }

        if (strongest != null) this.animalList.add(strongest);
        if (bride != null) this.animalList.add(bride);
    }

    static Comparator<SingleField> compareByDeathCount = (o1, o2) -> {
        if (o1.deathCount != o2.deathCount)
            return o1.deathCount - o2.deathCount;
        else if (o1.grassPriority != o2.grassPriority)
            return o1.grassPriority - o2.grassPriority;
        else
            return o1.fieldPos.compareTo(o2.fieldPos);
    };

    static Comparator<SingleField> compareByEquatorProximity = (o1, o2) -> {
        int equator = o1.worldMap.getSimulationOptions().mapSizeY() / 2;
        int distance1 = Math.abs(o1.fieldPos.y - equator);
        int distance2 = Math.abs(o2.fieldPos.y - equator);
        if (distance1 != distance2)
            return distance1 - distance2;
        else if (o1.fieldPos.y != o2.fieldPos.y)
            return o1.fieldPos.y - o2.fieldPos.y;
        else if (o1.grassPriority != o2.grassPriority)
            return o1.grassPriority - o2.grassPriority;
        else
            return o1.fieldPos.compareTo(o2.fieldPos);
    };

    public String objectDescription() {
        if (this.animalList.size() > 1)
            return animalList.last().objectDescription() + "\n+ " + (animalList.size() - 1) + "other";
        else if (this.animalList.size() == 1) return animalList.last().objectDescription();
        else if (this.grassPresent) return "";
        else return "";
    }

    public String textureName() {
        return switch (this.animalList.size()) {
            case 0 -> "empty";
            case 1 -> this.animalList.first().textureName();
            case 2 -> "animals_2";
            case 3 -> "animals_3";
            case 4 -> "animals_4";
            case 5 -> "animals_5";
            default -> "animals_many";
        };
    }

    public String backgroundTextureName() {
        return this.grassPresent ? "grass" : "background";
    }
}
