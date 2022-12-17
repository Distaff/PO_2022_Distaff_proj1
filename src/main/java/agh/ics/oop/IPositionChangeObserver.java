package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPos, Vector2d newPos, IMapElement objectRef);
}
