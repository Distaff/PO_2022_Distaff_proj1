package agh.ics.oop;

import java.util.TreeSet;

public class SingleField {
    TreeSet<Animal> animalList = new TreeSet<>(Animal::compareTo);

    boolean grassPresent;

    void breed(){

    }

    String textureName(){
        return "";  //TODO
    }

    String BackgroundTextureName(){
        return "";  //TODO
    }
}
