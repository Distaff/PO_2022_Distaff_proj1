package agh.ics.oop;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    private MapVisualizer drawEngine = new MapVisualizer(this);
    private MapBoundary mapBoundary = new MapBoundary();

    public MapBoundary getMapBoundary() { return this.mapBoundary; };

    public GrassField(int grassCount){
        Random gen = new Random();
        int border = (int) sqrt(grassCount*10);

        /*  //DEBUG
            Vector2d posD = new Vector2d(2, 2);
            Grass toAddD = new Grass(posD);
            entityMap.put(posD, toAddD);
            mapBoundary.place(toAddD);
        */

        for(; grassCount > 0; grassCount--) {
            //try to add until adding is successful
            while(true){
                Vector2d pos = new Vector2d(gen.nextInt(border), gen.nextInt(border));
                if(objectAt(pos) instanceof Grass) continue;
                Grass toAdd = new Grass(pos);
                entityMap.put(pos, toAdd);
                mapBoundary.place(toAdd);
                break;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public boolean place(Animal animal){
        boolean result = super.place(animal);
        if(result){
            animal.addObserver((IPositionChangeObserver) mapBoundary);
            mapBoundary.place(animal);
        }
        return result;
    }

    @Override
    public Vector2d getLowerLeft(){ return this.mapBoundary.getLowerLeftBoundary(); }
    @Override
    public Vector2d getUpperRight(){ return this.mapBoundary.getUpperRightBoundary(); }

}
