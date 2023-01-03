package agh.ics.oop;

import agh.ics.oop.gui.SimulationUIApp;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Runnable {

    final int FRAME_TIME = 100;
    private final IWorldMap worldMap;
    private final MotherNature motherNature;
    private final SimulationUIApp UIhandle;
    private int simulationID;
    private boolean paused = false;
    private boolean terminate = false;

    public IWorldMap getWorldMap() { return worldMap; }
    public void setPause(boolean val) { this.paused = val; }

    public boolean isPaused() { return this.paused; }

    public SimulationEngine(SimulationOptions simulationOptions, SimulationUIApp UIhandle){
        if(simulationOptions.cursedGateway())
            this.worldMap = new CursedGatewayMap(simulationOptions);
        else
            this.worldMap = new RoundEarthMap(simulationOptions);

        if(simulationOptions.toxicCorpses())
            this.motherNature = new MotherNature(worldMap, SingleField.compareByDeathCount);
        else
            this.motherNature = new MotherNature(worldMap, SingleField.compareByEquatorProximity);

        this.UIhandle = UIhandle;

        placeAnimals();
    }
    public void run() {
        while(!this.terminate){
            step();
                synchronized (this){
                    if(this.paused){
                        try { this.wait(); }
                        catch(InterruptedException e){ return; }
                    continue;   //omit delay when unpausing or stepping frame-by-frame
                }
            }
            try { Thread.sleep(FRAME_TIME); }
            catch(InterruptedException e){ return; }
        }
    }

    private void placeAnimals(){
        List<Vector2d> availablePositions = new ArrayList<>();
        for(SingleField field : worldMap.getAllFields())
            availablePositions.add(field.fieldPos);
        Collections.shuffle(availablePositions);

        for(int i = worldMap.getSimulationOptions().beginningAnimalCount(); i > 0; i--)
            worldMap.place(new Animal(worldMap, availablePositions.remove(availablePositions.size() - 1)));
    }
    private void step(){
        //1: Grim Reaper Phase
        for(Animal animal : worldMap.getAnimalsOnMap())
            animal.meetGrimReaper();

        //2: Movement phase
        for(Animal animal : worldMap.getAnimalsOnMap())
            animal.move();

        //3 & 4: Eating phase and breeding phase
        for(SingleField field : worldMap.getOccupiedFields())
            field.eatAndBreed();

        //5: Growth phase
        motherNature.plantGrass(worldMap.getSimulationOptions().dailyGrassCount());

        //6: Refresh UI
        Platform.runLater(UIhandle::updateGuiMap);
    }
}
