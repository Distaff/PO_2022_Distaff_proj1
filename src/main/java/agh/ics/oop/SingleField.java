package agh.ics.oop;

import java.util.List;
import java.util.TreeSet;

public class SingleField implements Comparable {

    //TODO: Trawa
    private final IWorldMap worldMap;
    private final Vector2d fieldPos;

    private TreeSet<Animal> animalList = new TreeSet<>(Animal::compareTo);
    boolean grassPresent;
    int deathCount = 0;
    public SingleField(IWorldMap worldMap, Vector2d fieldPos) {
        this.worldMap = worldMap;
        this.fieldPos = fieldPos;
    }

    public void grimReaperHasCame(){ this.deathCount++; }
    public int getDeathCount() { return deathCount; }

    public List<Animal> getAnimalsOnField(){
        return animalList.stream().toList();
    }

    public void pushAnimal(Animal animal){
        this.animalList.add(animal);
    }

    public void popAnimal(Animal animal){ this.animalList.remove(animal); }

    public void growGrass(){ grassPresent = true; };

    public void eatAndBreed(){
        Animal strongest = animalList.pollLast();
        Animal bride = animalList.pollLast();

        if(strongest != null && grassPresent){
            grassPresent = false;
            strongest.eatGrass();
        }
        if(strongest != null && bride != null){
            animalList.add(new Animal(strongest, bride));
        }

        if(strongest != null) animalList.add(strongest);
        if(bride != null) animalList.add(bride);
    }

    public boolean isEmpty() {
        return animalList.isEmpty() || grassPresent;
    }

    public String objectDescription(){
        if (animalList.size() > 1) return animalList.last().objectDescription() + "and " + animalList.size() + "other";
        else if (animalList.size() == 1) return animalList.last().objectDescription();
        else return "";
    }

    public String textureName(){
        return "";  //TODO
    }

    public String BackgroundTextureName(){
        return "";  //TODO
    }

    @Override
    public int compareTo(Object otherObject) {
        if(otherObject == null)
            throw new NullPointerException("Null Pointer Exception during fields comparison");

        if(!(otherObject instanceof SingleField))
            throw new IllegalArgumentException("Non-fields entity provided during fields comparison");

        SingleField other = (SingleField) otherObject;

        if(this.deathCount != other.deathCount)
            return this.deathCount - other.deathCount;
        else
            return this.fieldPos.compareTo(other.fieldPos);
    }
}
