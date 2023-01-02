package agh.ics.oop;

import agh.ics.oop.gui.App;

public class SimulationEngine implements IEngine, Runnable {

    final int FRAME_TIME = 250;
    final int IDLE_TIME = 50;

    private final IWorldMap worldMap;
    private final Runnable GUIApp;
    private int simulationID;

    private boolean paused = false;
    private int nextFrames = 0;
    private boolean terminate = false;

    public SimulationEngine(SimulationOptions simulationOptions, int simulationID){
        if(simulationOptions.cursedGateway()) worldMap = new CursedGatewayMap(simulationOptions);
        else worldMap = new RoundEarthMap(simulationOptions);

        this.GUIApp = new App(this, this.worldMap);
        this.simulationID = simulationID;

        this.GUIApp.run();
    }

    public void run() {
        while(!this.terminate){
            if(!this.paused){
                step();
                try { Thread.sleep(FRAME_TIME); }
                catch(InterruptedException e){ return; }
            }
            else if (nextFrames > 0) {
                nextFrames--;
                step();
            }
            else {
                try { Thread.sleep(IDLE_TIME); }
                catch(InterruptedException e){ return; }
            }

        }
    }
    private void step(){
        //1: Grim Reaper Phase
        for(Animal animal : worldMap.getAnimalsOnMap())
            animal.checkIfDying();

        //2: Movement phase
        for(Animal animal : worldMap.getAnimalsOnMap())
            animal.move();

        //3 & 4: Eating phase and breeding phase
        for(SingleField field : worldMap.getOccupiedFields())
            field.eatAndBreed();

        //5: Growth phase
        //TODO
    }
    public void togglePause() { this.paused = !this.paused; }
    public void stepOneFrame() { this.nextFrames++; }
    public void terminate() { this.terminate = true; }
}
