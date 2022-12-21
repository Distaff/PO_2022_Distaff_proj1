package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    IWorldMap worldMap;
    Rotations[] commands;
    ArrayList<Animal> animalList = new ArrayList<>();

    public SimulationEngine(Rotations[] _commands, IWorldMap _worldMap, Vector2d[] startingPositons){
    }

    public void run() {
    }
}
