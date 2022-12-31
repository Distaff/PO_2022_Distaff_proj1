package agh.ics.oop;

public class RoundEarthMap extends AbstractWorldMap {
    public Vector2d stepsAt(Animal animal, Vector2d newPos){
        Vector2d oldPos = animal.getPos();
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