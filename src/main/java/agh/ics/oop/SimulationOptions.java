package agh.ics.oop;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public record SimulationOptions(
        boolean cursedGateway,      //0 for earth, 1 for cursed gateway
        boolean toxicCorpses,       //0 for living equators, 1 for toxic corpses
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
) {

    static public SimulationOptions GetOptionsFromFile(String filepath) {
        File configFile = new File(filepath);
        return GetOptionsFromFile(configFile);
    }


    static public SimulationOptions GetOptionsFromFile(File file) {
        //Default values
        boolean cursedGateway = false;
        boolean toxicCorpses = false;
        boolean heavyMutations = false;
        boolean crazyBehavior = false;
        int mapSizeX = 12;
        int mapSizeY = 12;
        int beginningGrassCount = 24;
        int dailyGrassCount = 12;
        int singleGrassEnergy = 25;
        int beginningAnimalCount = 12;
        int breedingCost = 5;
        int minBreedingEnergy = 10;
        int minMutationCount = 2;
        int maxMutationCount = 4;
        int genotypeSize = 8;

        try {
            Scanner configReader = new Scanner(file);
            while (configReader.hasNextLine()) {
                String key = configReader.next();
                switch (key) {
                    case "cursedGateway":
                        cursedGateway = configReader.nextBoolean();
                        break;
                    case "toxicCorpses":
                        toxicCorpses = configReader.nextBoolean();
                        break;
                    case "heavyMutations":
                        heavyMutations = configReader.nextBoolean();
                        break;
                    case "crazyBehavior":
                        crazyBehavior = configReader.nextBoolean();
                        break;
                    case "mapSizeX":
                        mapSizeX = configReader.nextInt();
                        break;
                    case "mapSizeY":
                        mapSizeY = configReader.nextInt();
                        break;
                    case "beginningGrassCount":
                        beginningGrassCount = configReader.nextInt();
                        break;
                    case "dailyGrassCount":
                        dailyGrassCount = configReader.nextInt();
                        break;
                    case "singleGrassEnergy":
                        singleGrassEnergy = configReader.nextInt();
                        break;
                    case "beginningAnimalCount":
                        beginningAnimalCount = configReader.nextInt();
                        break;
                    case "breedingCost":
                        breedingCost = configReader.nextInt();
                        break;
                    case "minBreedingEnergy":
                        minBreedingEnergy = configReader.nextInt();
                        break;
                    case "minMutationCount":
                        minMutationCount = configReader.nextInt();
                        break;
                    case "maxMutationCount":
                        maxMutationCount = configReader.nextInt();
                        break;
                    case "genotypeSize":
                        genotypeSize = configReader.nextInt();
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Warning: file not found. Using default values.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        return new SimulationOptions(
                cursedGateway,
                toxicCorpses,
                heavyMutations,
                crazyBehavior,
                mapSizeX,
                mapSizeY,
                beginningGrassCount,
                dailyGrassCount,
                singleGrassEnergy,
                beginningAnimalCount,
                breedingCost,
                minBreedingEnergy,
                minMutationCount,
                maxMutationCount,
                genotypeSize
        );
    }
}
