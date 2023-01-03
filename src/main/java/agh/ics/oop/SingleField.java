package agh.ics.oop;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class SingleField {

    public final IWorldMap worldMap;
    public final Vector2d fieldPos;
    private final int grassPriority;

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
    public void grimReaperHasCame(){ this.deathCount++; }
    public int getDeathCount() { return deathCount; }

    public List<Animal> getAnimalsOnField(){ return animalList.stream().toList(); }

    public void pushAnimal(Animal animal){ this.animalList.add(animal); }

    public void popAnimal(Animal animal){ this.animalList.remove(animal); }

    public void growGrass(){ grassPresent = true; };

    public boolean isEmpty() { return animalList.isEmpty() || grassPresent; }

    public void eatAndBreed(){
        //TreeSet<Animal> animalList2 = (TreeSet<Animal>) animalList.clone();
        Animal strongest = animalList.pollLast();
        Animal bride = animalList.pollLast();

        if(strongest != null && this.grassPresent){
            grassPresent = false;
            strongest.eatGrass();
        }
        if(strongest != null && bride != null && bride.getEnergy() > worldMap.getSimulationOptions().minBreedingEnergy()){
            animalList.add(new Animal(strongest, bride));
            //System.out.println(animalList2);
        }

        if(strongest != null) animalList.add(strongest);
        if(bride != null) animalList.add(bride);
    }

    static Comparator<SingleField> compareByDeathCount = (o1, o2) -> {
        if(o1.grassPresent != o2.grassPresent)
            return o1.grassPresent ? 1 : -1;    //When grass is present field should not be privileged
        else if(o1.deathCount != o2.deathCount)
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
        //if(o1.grassPresent != o2.grassPresent)
        //    return o1.grassPresent ? 1 : -1;    //When grass is present field should not be privileged
        if(distance1 != distance2)
            return distance1 - distance2;
        else if(o1.fieldPos.y != o2.fieldPos.y)
            return o1.fieldPos.y - o2.fieldPos.y;
        else if (o1.grassPriority != o2.grassPriority)
            return o1.grassPriority - o2.grassPriority;
        else
            return o1.fieldPos.compareTo(o2.fieldPos);
    };

    public String objectDescription(){
        if (animalList.size() > 1) return animalList.last().objectDescription() + "and " + animalList.size() + "other";
        else if (animalList.size() == 1) return animalList.last().objectDescription();
        else if (this.grassPresent) return "*";
        else return "__";
    }

    public String textureName(){
        return switch (this.animalList.size()){
            case 0 -> "empty";
            case 1 -> animalList.first().textureName();
            case 2 -> "animals_2";
            case 3 -> "animals_3";
            case 4 -> "animals_4";
            case 5 -> "animals_5";
            default -> "animals_many";
        };
    }

    public String backgroundTextureName(){
        return "background";
    }
}
