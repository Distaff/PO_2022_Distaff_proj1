package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    //add @Setup
    @Test
    public void _equals(){
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);

        assertEquals(testedObject0.equals(testedObject1), true);
        assertEquals(testedObject0.equals(testedObject2), false);
        assertEquals(testedObject1.equals(testedObject0), true);
        assertEquals(testedObject1.equals(testedObject2), false);
        assertEquals(testedObject2.equals(testedObject0), false);
        assertEquals(testedObject2.equals(testedObject1), false);
    }
    @Test
    public void _hashCode(){
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);

        assertTrue(testedObject0.hashCode() == testedObject1.hashCode());
        assertFalse(testedObject0.hashCode() == testedObject2.hashCode());
        assertTrue(testedObject1.hashCode() == testedObject0.hashCode());
        assertFalse(testedObject1.hashCode() == testedObject2.hashCode());
        assertFalse(testedObject2.hashCode() == testedObject0.hashCode());
        assertFalse(testedObject2.hashCode() == testedObject1.hashCode());
    }

    @Test
    public void _toString(){
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(-1 ,0);

        assertEquals(testedObject0.toString(), "(0, 1)");
        assertEquals(testedObject1.toString(), "(-1, 0)");
    }

    @Test
    public void _precedes() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        assertTrue(testedObject0.precedes(testedObject1));
        assertTrue(testedObject1.precedes(testedObject0));
        assertFalse(testedObject2.precedes(testedObject0));
        assertFalse(testedObject0.precedes(testedObject2));
        assertTrue(testedObject3.precedes(testedObject0));
        assertFalse(testedObject0.precedes(testedObject3));
    }

    @Test
    public void _follows() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        assertTrue(testedObject0.follows(testedObject1));
        assertTrue(testedObject1.follows(testedObject0));
        assertFalse(testedObject2.follows(testedObject0));
        assertFalse(testedObject0.follows(testedObject2));
        assertFalse(testedObject3.follows(testedObject0));
        assertTrue(testedObject0.follows(testedObject3));
    }

    @Test
    public void _upperRight() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        Vector2d expectedObject1 = new Vector2d(0 ,1);
        Vector2d expectedObject2 = new Vector2d(1 ,1);
        Vector2d expectedObject3 = new Vector2d(0 ,1);

        assertEquals(testedObject0.upperRight(testedObject1), expectedObject1);
        assertEquals(testedObject1.upperRight(testedObject0), expectedObject1);
        assertEquals(testedObject0.upperRight(testedObject2), expectedObject2);
        assertEquals(testedObject2.upperRight(testedObject0), expectedObject2);
        assertEquals(testedObject0.upperRight(testedObject3), expectedObject3);
        assertEquals(testedObject3.upperRight(testedObject0), expectedObject3);
    }

    @Test
    public void _lowerLeft() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        Vector2d expectedObject1 = new Vector2d(0 ,1);
        Vector2d expectedObject2 = new Vector2d(0 ,0);
        Vector2d expectedObject3 = new Vector2d(-1 ,0);

        assertEquals(testedObject0.lowerLeft(testedObject1), expectedObject1);
        assertEquals(testedObject1.lowerLeft(testedObject0), expectedObject1);
        assertEquals(testedObject0.lowerLeft(testedObject2), expectedObject2);
        assertEquals(testedObject2.lowerLeft(testedObject0), expectedObject2);
        assertEquals(testedObject0.lowerLeft(testedObject3), expectedObject3);
        assertEquals(testedObject3.lowerLeft(testedObject0), expectedObject3);
    }

    @Test
    public void _add() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        Vector2d expectedObject1 = new Vector2d(0 ,2);
        Vector2d expectedObject2 = new Vector2d(1 ,1);
        Vector2d expectedObject3 = new Vector2d(-1 ,1);

        assertEquals(testedObject0.add(testedObject1), expectedObject1);
        assertEquals(testedObject1.add(testedObject0), expectedObject1);
        assertEquals(testedObject0.add(testedObject2), expectedObject2);
        assertEquals(testedObject2.add(testedObject0), expectedObject2);
        assertEquals(testedObject0.add(testedObject3), expectedObject3);
        assertEquals(testedObject3.add(testedObject0), expectedObject3);
    }

    @Test
    public void _subtract() {
        Vector2d testedObject0 = new Vector2d(0 ,1);
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        Vector2d expectedObject1 = new Vector2d(0 ,0);
        Vector2d expectedObject1r = new Vector2d(0 ,0);
        Vector2d expectedObject2 = new Vector2d(-1 ,1);
        Vector2d expectedObject2r = new Vector2d(1 ,-1);
        Vector2d expectedObject3 = new Vector2d(1 ,1);
        Vector2d expectedObject3r = new Vector2d(-1 ,-1);

        assertEquals(testedObject0.subtract(testedObject1), expectedObject1);
        assertEquals(testedObject1.subtract(testedObject0), expectedObject1r);
        assertEquals(testedObject0.subtract(testedObject2), expectedObject2);
        assertEquals(testedObject2.subtract(testedObject0), expectedObject2r);
        assertEquals(testedObject0.subtract(testedObject3), expectedObject3);
        assertEquals(testedObject3.subtract(testedObject0), expectedObject3r);
    }

    @Test
    public void _opposite() {
        Vector2d testedObject1 = new Vector2d(0 ,1);
        Vector2d testedObject2 = new Vector2d(1 ,0);
        Vector2d testedObject3 = new Vector2d(-1 ,0);

        Vector2d expectedObject1 = new Vector2d(0 ,-1);
        Vector2d expectedObject2 = new Vector2d(-1 ,0);
        Vector2d expectedObject3 = new Vector2d(1 ,0);

        assertEquals(testedObject1.opposite(), expectedObject1);
        assertEquals(testedObject2.opposite(), expectedObject2);
        assertEquals(testedObject3.opposite(), expectedObject3);
    }
}
