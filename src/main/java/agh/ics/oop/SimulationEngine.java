package agh.ics.oop;

import agh.ics.oop.gui.App;

public class SimulationEngine implements Runnable {

    final int FRAME_TIME = 250;
    private final IWorldMap worldMap;
    private final Thread GUIThread;
    private final Runnable GUIApp;  //TODO: zmienic nazwe
    private int simulationID;

    //TODO wait, notify do synchronizacji: paused=true do pauzy, notify + paused=false do obudzenia, samo notify to stepu
    private boolean paused = false;
    private boolean terminate = false;

    public SimulationEngine(SimulationOptions simulationOptions, int simulationID){
        if(simulationOptions.cursedGateway()) worldMap = new CursedGatewayMap(simulationOptions);
        else worldMap = new RoundEarthMap(simulationOptions);

        this.GUIApp = new App(this, this.worldMap);
        this.simulationID = simulationID;

        GUIThread = new Thread(this.GUIApp);
    }

    public void run() {
        while(!this.terminate){
            step();
                synchronized (this){
                    if(this.paused){
                    try { this.wait(); }
                    catch(InterruptedException e){ return; }
                }
                continue;   //omit delay when unpausing or stepping frame-by-frame
            }

            try { Thread.sleep(FRAME_TIME); }
            catch(InterruptedException e){ return; }
        }
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
        //TODO
    }
    public void togglePause() { this.paused = !this.paused; }
}
