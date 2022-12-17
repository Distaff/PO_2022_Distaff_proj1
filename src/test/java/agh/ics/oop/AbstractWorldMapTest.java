package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractWorldMapTest {
    @Test
    void _isOccuppied_place(){
        AbstractWorldMap testedObject1 = new GrassField(0);

        assertFalse(testedObject1.isOccupied(new Vector2d(1,1)));

        Animal dummyAnimal = new Animal(testedObject1, new Vector2d(1,1));
        testedObject1.place(dummyAnimal);

        assertTrue(testedObject1.isOccupied(new Vector2d(1,1)));
    }

    @Test
    void _positionChanged(){
        AbstractWorldMap testedObject1 = new GrassField(0);
        Animal dummyAnimal = new Animal(testedObject1, new Vector2d(1,1));
        testedObject1.place(dummyAnimal);

        assertTrue(testedObject1.isOccupied(new Vector2d(1,1)));

        dummyAnimal.move(MoveDirection.FORWARD);

        assertFalse(testedObject1.isOccupied(new Vector2d(1,1)));
        assertTrue(testedObject1.isOccupied(new Vector2d(1,2)));
    }
}