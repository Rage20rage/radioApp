package de.marko.radio.client.network;

import de.marko.radio.client.main.Main;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Network {

    private static Network network;

    public static Network getInstance() {
        if(network == null) {
            network = new Network();
        }
        if(network.networkHandler == null) {
            network.networkHandler = new NetworkHandler();
        }
        return network;
    }
    public NetworkHandler networkHandler;
    Logger logger = Main.logger;

    public ArrayList<String> stationNames = new ArrayList<>();
    public ArrayList<String> stationURLs = new ArrayList<>();

    public void initStreams() {
        logger.info("Inizialisiere Radiostreams...");
        if(!networkHandler.isConnected()) {
            networkHandler.connect();
        }
        networkHandler.send("Radio-getRadioStations");
        long stationCount = Long.parseLong(networkHandler.recive());
        for (int i = 0; i < stationCount; i++) {
            stationNames.add(i, networkHandler.recive());
            stationURLs.add(i, networkHandler.recive());
        }
        logger.info("RadioStreams inizialisiert!");
    }

}
