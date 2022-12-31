package agh.ics.oop;

import java.util.List;
import java.util.TreeSet;

public class SingleField {

    //TODO: Trawa, umieranie
    private final IWorldMap worldMap;
    private final Vector2d fieldPos;

    private TreeSet<Animal> animalList = new TreeSet<>(Animal::compareTo);

    boolean grassPresent;
    public SingleField(IWorldMap worldMap, Vector2d fieldPos) {
        this.worldMap = worldMap;
        this.fieldPos = fieldPos;
    }

    public List<Animal> getAnimalsOnField(){
        return animalList.stream().toList();
    }

    void pushAnimal(Animal animal){
        this.animalList.add(animal);
    }

    void popAnimal(Animal animal){
        this.animalList.remove(animal);
    }

    void eatAndBreed(){
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

    String objectDescription(){
        if (animalList.size() > 1) return animalList.last().objectDescription() + "and " + animalList.size() + "other";
        else if (animalList.size() == 1) return animalList.last().objectDescription();
        else return "";
    }

    String textureName(){
        return "";  //TODO
    }

    String BackgroundTextureName(){
        return "";  //TODO
    }
}
