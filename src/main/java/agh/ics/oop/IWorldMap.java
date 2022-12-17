package agh.ics.oop;

import java.util.Collection;

public interface IWorldMap {
    /**
     * Place an entity on the map. If entity can move, it should provide entityInfoPublisher.
     *
     * @param entity
     *            The entity to place on the map.
     * @param entityInfoPublisher
     *            Object which will provide info about entity movement through IPositionChangeObserver.
     *            This can be null if entity is immovable.
     * @return True if the entity was placed.
     */
    boolean place(IMapElement entity, IMovableEntity entityInfoPublisher);


    /**
     * Informs entity about resulting position, if it tries to move to specific position.
     * In most cases, desiredPos will be equal to resultingPos, but sometimes it will be different.
     * Example: Animal goes out of map bounds, and is teleported to another edge of the map.
     *
     * @param desiredPos
     *            The entity to place on the map.
     * @return Resulting position
     */
    Vector2d resultingPos(Vector2d desiredPos);

    /**
     * Return true if given position on the map is occupied by any entity.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    Collection<IMapElement> getEntitiesOnMap();
}
