package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void next(){
        MapDirection testedObject = MapDirection.NORTH;

        testedObject = testedObject.next();
        assertEquals(testedObject, MapDirection.EAST);
        testedObject = testedObject.next();
        assertEquals(testedObject, MapDirection.SOUTH);
        testedObject = testedObject.next();
        assertEquals(testedObject, MapDirection.WEST);
        testedObject = testedObject.next();
        assertEquals(testedObject, MapDirection.NORTH);
    }
    @Test
    public void prev(){
        MapDirection testedObject = MapDirection.NORTH;

        testedObject = testedObject.prev();
        assertEquals(testedObject, MapDirection.WEST);
        testedObject = testedObject.prev();
        assertEquals(testedObject, MapDirection.SOUTH);
        testedObject = testedObject.prev();
        assertEquals(testedObject, MapDirection.EAST);
        testedObject = testedObject.prev();
        assertEquals(testedObject, MapDirection.NORTH);
    }
}
