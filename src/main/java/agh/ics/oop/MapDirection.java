package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    final String[] NAMES = {"North", "East", "South", "West"};
    final String[] SHORT_NAMES = {"^", ">", "<", "v"};
    public String toString(){
        return NAMES[this.ordinal()];
    }

    public String shortRepresentation (){
        return SHORT_NAMES[this.ordinal()];
    }

    MapDirection next(){
        return this.values()[(this.ordinal() + 1) % this.values().length];
    }

    MapDirection prev(){
        if (this.ordinal() > 0){
            return this.values()[this.ordinal() - 1];
        }
        else return this.values()[this.values().length - 1];
    }

    Vector2d toUnitVector(){
        switch(this){
            case NORTH:
                return new Vector2d(0,1);
            case EAST:
                return new Vector2d(1,0);
            case SOUTH:
                return new Vector2d(0,-1);
            case WEST:
                return new Vector2d(-1,0);
        }
        return null;
    }
}
