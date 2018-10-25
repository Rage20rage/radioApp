package de.marko.radio.client.config;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionController {

    public static Logger logger = Logger.getRootLogger();

    private static VersionController ourInstance = new VersionController();

    public static VersionController getInstance() {
        return ourInstance;
    }

    public double getVersion() {
        logger.debug("Lade URL zum lesen der Version...");
        try {
            URL url = new URL("https://minetownmc.de/radio/radio.version");
            logger.info("Eröffne Web-stream...");
            InputStream inputStream = url.openStream();
            logger.debug("Inizialisiere URL reader um die Version zu lesen...");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            logger.info("Version wird gelesen...");
            double version = Double.parseDouble(bufferedReader.readLine());
            logger.info("Version wurde gelesen!");
            return version;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.warn("Die Version konnte geladen überprüft werden!");
        return 0.0;
    }

    public boolean checkVersion() {
        logger.info("Überprüfe Client Version mit der Server-Version...");
        double clientVersion = Double.parseDouble(ConfigHandler.getInstance().getConfig("system", "version"));
        double serverVersion = getVersion();
        logger.debug("Gleiche Versionen ab...");
        if(clientVersion >= serverVersion) {
            logger.info("Sie verwenden eine Aktuelle Version!");
            return true;
        }
        logger.warn("Ihre Version ist veraltet!");
        return false;
    }
}
