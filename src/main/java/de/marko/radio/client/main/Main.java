package de.marko.radio.client.main;

import de.marko.radio.client.gui.WindowManager;
import de.marko.radio.client.network.Network;
import de.marko.radio.client.player.PlayerManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    public static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("debug")) {
                logger.setLevel(Level.ALL);
                logger.info("Debug wurde aktiviert!");
            }
        }
        Inizialiser.inizialise();
        Network.getInstance().initStreams();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Network.getInstance().networkHandler.disconnect();
            }
        });
        for (int i = 0; i < Network.getInstance().stationNames.size(); i++) {
            System.out.println(Network.getInstance().stationNames.get(i) + " : " + Network.getInstance().stationURLs.get(i));
        }
        WindowManager.getInstance().startMainWindow();
        //---------------------------------------------------------------------------------------------------------------------------------------------------------
        while (true) {
            Scanner scanner = new Scanner(System.in);
            logger.info("Bitte gben sie den gewünschten Sender ein: ");
            String stationName = scanner.nextLine();
            if(stationName.equalsIgnoreCase("stop")) {
                System.exit(0);
            }
            int urlPosition = getURLPosition(stationName);
            if(urlPosition == -1) {
                logger.warn("Dieser Sender existiert nicht!");
            } else {
                PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(urlPosition));
            }
        }
        //---------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    private static int getURLPosition(String stationName) {
        int i = 0;
        for (String name : Network.getInstance().stationNames) {
            if(name.equals(stationName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

}
