package agh.ics.oop;

import java.util.Random;

public class CursedGatewayMap extends AbstractWorldMap {
    public Vector2d stepsAt(Animal animal, Vector2d newPos){
        Vector2d oldPos = animal.getPos();
        if( !newPos.precedes(new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY())) ||
            !newPos.follows(new Vector2d(0, 0)))
        {
            Random rand = new Random();
            newPos = new Vector2d(rand.nextInt(simulationOptions.mapSizeX()), rand.nextInt(simulationOptions.mapSizeY()));
        }

        moveAnimal(animal, newPos);

        fieldAt(newPos).popAnimal(animal);
        animal.teleported();    //apply energy penalty
        fieldAt(newPos).pushAnimal(animal);

        return newPos;
    }
}
