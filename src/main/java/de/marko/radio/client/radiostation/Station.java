package de.marko.radio.client.radiostation;

import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

public class Station {

    Logger logger = Main.logger;

    public Station(String name, String url) {
        this.name = name;
        this.url = url;
    }

    private String url;
    private String name;

    public String getName() {
        logger.debug("Hole den Namen der Aktuellen Radiostation...");
        return name;
    }

    public String getUrl() {
        logger.debug("Hole die URL der Aktuellen Radiostation...");
        return url;
    }

}
