package agh.ics.oop;

public class RoundEarthMap extends AbstractWorldMap {
    public RoundEarthMap(SimulationOptions simulationOptions) { super(simulationOptions); }

    public Vector2d stepsAt(Animal animal, Vector2d newPos){
        if(newPos == null){
            fieldAt(animal.getPos()).popAnimal(animal);
            return null;
        }

        int maxX = simulationOptions.mapSizeX() - 1;
        int maxY = simulationOptions.mapSizeY() - 1;

        if(newPos.x < 0)
            newPos = new Vector2d(maxX, newPos.y);
        else if(newPos.x > maxX)
            newPos = new Vector2d(0, newPos.y);

        if(newPos.y < 0)
            newPos = new Vector2d(newPos.x, maxY);
        else if(newPos.y > maxY)
            newPos = new Vector2d(newPos.x, 0);

        moveAnimal(animal, newPos);
        return newPos;
    }
}