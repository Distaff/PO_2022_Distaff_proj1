package agh.ics.oop;

import java.util.Collection;

public interface IWorldMap {
    /**
     * Place an animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the entity was placed.
     */
    boolean place(Animal animal);


    /**
     * Informs map about the fact, that entity is changing position.
     * In some cases (teleportation in CursedGateway) this will result with energy loss.
     * Example: Animal goes out of map bounds, and is teleported to another edge of the map.
     *
     * @param pos
     *            Position on which the animal wants to go
     * @return Resulting position
     */
    Vector2d stepsAt(Vector2d pos);

    /**
     * Return true if given position on the map is occupied by any entity.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    SimulationOptions getSimulationOptions();
    int getWorldAge();
    Collection<Animal> getAnimalsOnMap();
}
