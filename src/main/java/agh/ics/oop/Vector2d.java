package agh.ics.oop;

public class Vector2d implements Comparable {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return ("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
    }

    boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    @Override public boolean equals(Object other){
        if(other == null)
            return false;

        if(other instanceof Vector2d){
            Vector2d otherVector = (Vector2d)other;
            return this.x == otherVector.x && this.y == otherVector.y;
        }
        return false;
    }

    Vector2d opposite(){
        return new Vector2d(0 - this.x, 0 - this.y);
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.x)*31 + Integer.hashCode(this.y)*11;
    }

    @Override
    public int compareTo(Object otherObject) {
        if(otherObject == null)
            throw new NullPointerException("Null Pointer Exception during vectors comparison");

        if(!(otherObject instanceof Vector2d))
            throw new IllegalArgumentException("Non-vector object provided during vectors comparison");

        Vector2d other = (Vector2d) otherObject;

        if(this.x != other.x)
            return this.x - other.x;
        else
            return this.y - other.y;
    }
}
