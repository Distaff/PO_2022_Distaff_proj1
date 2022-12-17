package agh.ics.oop;

public interface IMovableEntity {
    public void addObserver(IPositionChangeObserver added);

    public void removeObserver(IPositionChangeObserver removed);
}
