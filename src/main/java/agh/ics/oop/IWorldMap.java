package agh.ics.oop;

import java.util.Collection;
import java.util.List;

public interface IWorldMap {
    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the entity was placed.
     */
    boolean place(Animal animal);


    void grassHasGrown(Vector2d pos);

    /**
     * Informs map about the fact, that entity is changing position or is removed.
     * In some cases (teleportation in CursedGateway) this will result in energy loss.
     * Example: Animal goes out of map bounds, and is teleported to another edge of the map.
     *
     * @param animal Which animal wants to change position
     * @param newPos Position on which the animal wants to go. Null if animal should be removed from map.
     * @return Resulting position, or null if animal has been removed.
     */
    Vector2d stepsAt(Animal animal, Vector2d newPos);

    /**
     * Informs map about the fact, that Animal has died.
     *
     * @param animal Which animal is dying.
     */
    void animalDies(Animal animal);

    /**
     * Return true if given position on the map is occupied by any entity.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    SimulationOptions getSimulationOptions();

    int getWorldAge();

    List<SingleField> getAllFields();

    List<SingleField> getOccupiedFields();

    List<Animal> getAnimalsOnMap();

    String stats();
}
