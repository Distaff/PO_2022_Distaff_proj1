package agh.ics.oop;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver{
    private SortedSet<PositionHolder> positionsByX = new TreeSet<>(PositionHolder.comparareXFirst);
    private SortedSet<PositionHolder> positionsByY = new TreeSet<>(PositionHolder.comparareYFirst);

    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos, IMapElement objectRef) {
        positionsByX.add(new PositionHolder(newPos, objectRef));
        positionsByX.remove(new PositionHolder(oldPos, objectRef));
        positionsByY.add(new PositionHolder(newPos, objectRef));
        positionsByY.remove(new PositionHolder(oldPos, objectRef));
    }

    public void place(IMapElement object) {
        positionsByX.add(new PositionHolder(object.getPos(), object));
        positionsByY.add(new PositionHolder(object.getPos(), object));
    }
    Vector2d getUpperRightBoundary(){
        if (positionsByX.size() > 0)
            return positionsByX.last().pos.upperRight(positionsByY.last().pos);
        else
            return new Vector2d(0,0);
    }

    Vector2d getLowerLeftBoundary(){
        if (positionsByX.size() > 0)
            return positionsByX.first().pos.lowerLeft(positionsByY.first().pos);
        else
            return new Vector2d(0,0);
    }
}
