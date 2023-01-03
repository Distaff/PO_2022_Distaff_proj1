package agh.ics.oop;

import java.util.Random;

public class CursedGatewayMap extends AbstractWorldMap {
    public CursedGatewayMap(SimulationOptions simulationOptions){ super(simulationOptions); }

    public Vector2d stepsAt(Animal animal, Vector2d newPos){
        if(newPos == null){
            fieldAt(animal.getPos()).popAnimal(animal);
            return null;
        }

        if( !newPos.precedes(new Vector2d(this.simulationOptions.mapSizeX(), this.simulationOptions.mapSizeY())) ||
            !newPos.follows(new Vector2d(0, 0)))
        {
            Random rand = new Random();
            newPos = new Vector2d(rand.nextInt(simulationOptions.mapSizeX()), rand.nextInt(simulationOptions.mapSizeY()));
        }

        moveAnimal(animal, newPos);

        fieldAt(newPos).popAnimal(animal);
        animal.subtractEnergy(this.simulationOptions.breedingCost());    //apply energy penalty
        fieldAt(newPos).pushAnimal(animal);

        return newPos;
    }
}
