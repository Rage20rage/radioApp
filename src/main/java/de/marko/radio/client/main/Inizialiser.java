package de.marko.radio.client.main;

import de.marko.radio.client.config.ConfigHandler;
import de.marko.radio.client.config.VersionController;
import de.marko.radio.client.player.PlayerManager;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Inizialiser {

    private static Logger logger = Logger.getRootLogger();

    public static void inizialise() {
        ConfigHandler.getInstance().createConfigFile("system");
        ConfigHandler.getInstance().createConfigFile("network");
        if(ConfigHandler.getInstance().getConfig("system", "version") == null) {
            ConfigHandler.getInstance().setNewConfig("system", "version", String.valueOf(VersionController.getInstance().getVersion()));
            //ConfigHandler.getInstance().setNewConfig("system", "eula", "false");
            ConfigHandler.getInstance().setNewConfig("network", "serverURL", "ts.minetownmc.de");
            ConfigHandler.getInstance().setNewConfig("network", "serverPort", "2294");
            JOptionPane.showMessageDialog(null, "Die Configurationsdateien wurden erstellt! Bitte starten sie die Anwendung neu!",
                    "Neustart erfolderlich", JOptionPane.INFORMATION_MESSAGE);
            /*
            System.out.println();
            logger.fatal(" +-+-+-+ +-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+-+-+");
            logger.fatal(" |D|i|e| |E|u|l|a| |w|u|r|d|e| |n|i|c|h|t| |A|k|z|e|p|t|i|e|r|t|!|");
            logger.fatal( " +-+-+-+ +-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println();
            logger.fatal("Bitte setzen sie den Wert der Eula von false auf true in der system.config, \n" +
                    "falls sie der Eula zustimmen!");
            System.exit(0);
            */
        } else {
            /*if(ConfigHandler.getInstance().getConfig("system", "eula").equalsIgnoreCase("true")) {
                logger.info("Die Eula wurde Akzeptiert!");
            } else {
                System.out.println();
                logger.fatal(" +-+-+-+ +-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+-+-+");
                logger.fatal(" |D|i|e| |E|u|l|a| |w|u|r|d|e| |n|i|c|h|t| |A|k|z|e|p|t|i|e|r|t|!|");
                logger.fatal( " +-+-+-+ +-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+-+-+");
                System.out.println();
                logger.fatal("Bitte setzen sie den Wert der Eula von false auf true in der system.config, \n" +
                        "falls sie der Eula zustimmen!");
                System.exit(0);
            }*/
            if(!VersionController.getInstance().checkVersion()) {
                System.out.println();
                logger.warn(" +-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+    ");
                logger.warn(" |O|l|d| |V|e|r|s|i|o|n| |D|e|t|e|c|t|e|d|!|    ");
                logger.warn(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                logger.warn(" |P|l|e|a|s|e| |U|p|d|a|t|e| |t|h|i|s| |A|p|p|!|");
                logger.warn(" +-+-+-+-+-+-+ +-+-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+");
                System.out.println();
                PlayerManager.getInstance().playErrorSound();
                JOptionPane.showMessageDialog(null, "Sie verwenden ein alte Version!" +
                        "\nBitte updaten sie die Radio-APP demnächst!", "Alte Version", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logger.info("Sie verwenden die neuste Version: " + ConfigHandler.getInstance().getConfig("system", "version"));
            }
            PlayerManager.getInstance().setPlayerPosition(-1);
        }
    }

}
