package agh.ics.oop;

public record SimulationOptions(
        boolean mapMode,            //0 for earth, 1 for cursed gateway
        boolean grassPreferences,   //0 for living equators, 1 for toxic corpses
        boolean heavyMutations,     //0 for light correction, 1 for full randomness
        boolean crazyBehavior,      //0 for full predestination, 1 for craziness

        int mapSizeX,
        int mapSizeY,
        int beginningGrassCount,
        int dailyGrassCount,
        int singleGrassEnergy,
        int beginningAnimalCount,
        int breedingCost,
        int minBreedingEnergy,
        int minMutationCount,
        int maxMutationCount,
        int genotypeSize
) {}
