package agh.ics.oop;

import java.util.Random;

public enum Rotations {
    DEG0,
    DEG45,
    DEG90,
    DEG135,
    DEG180,
    DEG225,
    DEG270,
    DEG315;

    Rotations smallChange() {
        Random rand = new Random();  // nowy obiekt co wywołanie
        if (rand.nextInt(0, 1) == 1)
            return Rotations.values()[(this.ordinal() + 1) % Rotations.values().length];    //+1
        else
            return Rotations.values()[(this.ordinal() + 7) % Rotations.values().length];    //-1
    }

    static Rotations randomVal() {
        Random rand = new Random(); // nowy obiekt co wywołanie
        return Rotations.values()[rand.nextInt(Rotations.values().length)];
    }

    public String toString() {
        return Integer.toString(this.ordinal());
    }
}
