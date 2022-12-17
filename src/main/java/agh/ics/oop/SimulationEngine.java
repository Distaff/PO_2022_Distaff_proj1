package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    IWorldMap worldMap;
    MoveDirection[] commands;
    ArrayList<Animal> animalList = new ArrayList<>();

    public SimulationEngine(MoveDirection[] _commands, IWorldMap _worldMap, Vector2d[] startingPositons){
        this.commands = _commands;
        this.worldMap = _worldMap;
        for (Vector2d i: startingPositons) {
            animalList.add(new Animal(worldMap, i));
            worldMap.place(animalList.get(animalList.size() - 1));
        }
    }

    public void run() {
        for(int i = 0; i < commands.length; i++){
            animalList.get(i % animalList.size()).move(commands[i]);
        }
    }
}
