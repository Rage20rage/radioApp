package de.marko.radio.server.config;

import de.marko.radio.server.main.Main;
import de.marko.radio.server.network.NetworkHandler;

public class NetworkConfig {

    public static void writeServerPort() {
        String port = ConfigHandler.getInstance().getConfig("network" , "port");
        Main.logger.debug("Speichere Port in den Cache...");
        NetworkHandler.getInstance().port = Integer.parseInt(port);
    }

}
