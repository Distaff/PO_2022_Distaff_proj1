package agh.ics.oop;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    final String[] SYMBOLS = {"↑", "↗", "→","↘", "↓","↙", "\u2190", "↖"}; //"←" does not work for whatever reason
    final String[] NAMES = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    final Vector2d[] UNITVECTORS = {
            new Vector2d(0,1),
            new Vector2d(1,1),
            new Vector2d(1,0),
            new Vector2d(1,-1),
            new Vector2d(0,-1),
            new Vector2d(-1,-1),
            new Vector2d(-1,0),
            new Vector2d(-1,1),
    };

    public String toString(){ return SYMBOLS[this.ordinal()]; }
    public String shortRepresentation(){ return NAMES[this.ordinal()]; }

    MapDirection next(){ return this.rotate(1); }
    MapDirection prev(){ return this.rotate(-1); }

    MapDirection rotate(int rotationValue){
        int res = this.ordinal() + rotationValue;
        res %= this.values().length;
        res += this.values().length;    //dealing with negative values (for example: rotate(-5) on enum with value 1)
        res %= this.values().length;
        return this.values()[res];
    }

    MapDirection rotate(Rotations rotationValue){
        return this.rotate(rotationValue.ordinal());
    }

    Vector2d toUnitVector(){
        return UNITVECTORS[this.ordinal()];
    }
}
