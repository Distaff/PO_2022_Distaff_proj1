package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try {
            //INIT STAGE
            System.out.println("Initializing...");

            //MAIN STAGE
            System.out.println("System started.");



            Application.launch(App.class, args);


            //Terminating
            System.out.println("Done.");

        } catch(IllegalArgumentException ex) {
            System.err.println("Exception caught: " + ex);
            System.exit(-3);
        }
    }
}