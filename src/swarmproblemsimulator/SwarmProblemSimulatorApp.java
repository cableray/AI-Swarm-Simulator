/*
 * SwarmProblemSimulatorApp.java
 */

package swarmproblemsimulator;

import java.util.Random;
import java.util.Vector;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class SwarmProblemSimulatorApp extends SingleFrameApplication {

    //public TiledDiscreteSpace space;
    //public Vector<Ant> ants = new Vector<Ant>(12);
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //PheremoneDSN origin= new PheremoneDSN();
        //space = new TiledDiscreteSpace(origin);
        //space.generate(20, 20);
        //space.wrapEdges();
        //origin.setMaxAnts(100);
        //((PheremoneDSN)space.getNode(6, 15)).setFood(1000);
        //((PheremoneDSN)space.getNode(13, 4)).setFood(1000);
        //((PheremoneDSN)space.getNode(0, 0)).setPheremoneLevel(1000);
        //((PheremoneDSN)space.getNode(19, 19)).setMaxAnts(100);
        //for (int i=0; i<100; i++){
        //    ants.add(new TwoChoiceAnt(origin,new Random((1337+3)*i)));
        //}
        show(new SwarmProblemSimulatorView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SwarmProblemSimulatorApp
     */
    public static SwarmProblemSimulatorApp getApplication() {
        return Application.getInstance(SwarmProblemSimulatorApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(SwarmProblemSimulatorApp.class, args);
    }

}
