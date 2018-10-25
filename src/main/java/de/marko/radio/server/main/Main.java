package de.marko.radio.server.main;

import de.marko.radio.server.network.Listener;
import de.marko.radio.server.network.Sheduler;
import org.apache.log4j.*;

public class Main {

    public static Logger logger = Logger.getRootLogger();
    public static String version = "1.0";

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("debug")) {
                logger.setLevel(Level.ALL);
                logger.info("Debug wurde aktiviert!");
            }
        }
        Inizialiser.inizialize();
        Thread schedulerthread = new Thread(new Sheduler());
        schedulerthread.start();
        while (true) {
            Listener.waitForConnection();
        }
    }

}
