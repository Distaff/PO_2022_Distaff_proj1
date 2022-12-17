package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    IWorldMap worldMap;
    MoveDirection[] commands;
    ArrayList<Animal> animalList = new ArrayList<>();

    public SimulationEngine(MoveDirection[] _commands, IWorldMap _worldMap, Vector2d[] startingPositons){
    }

    public void run() {
    }
}
