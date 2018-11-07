package de.marko.radio.server.main;

import de.marko.radio.client.config.VersionController;
import de.marko.radio.server.MySQL.SQLHandler;
import de.marko.radio.server.config.ConfigHandler;
import de.marko.radio.server.config.MySQLConfig;
import de.marko.radio.server.config.NetworkConfig;

public class Inizialiser {

    public static void inizialize() {
        ConfigHandler.getInstance().createConfigFile("sql");
        ConfigHandler.getInstance().createConfigFile("network");
        ConfigHandler.getInstance().createConfigFile("system");
        if(ConfigHandler.getInstance().getConfig("system", "version") == null) {
            ConfigHandler.getInstance().setConfig("system", "version", Main.version);
            ConfigHandler.getInstance().setConfig("network", "port", "2294");
            ConfigHandler.getInstance().setConfig("sql", "url", "127.0.0.1");
            ConfigHandler.getInstance().setConfig("sql", "port", "3306");
            ConfigHandler.getInstance().setConfig("sql", "database", "Radio");
            ConfigHandler.getInstance().setConfig("sql", "user", "root");
            ConfigHandler.getInstance().setConfig("sql", "passwort", "");
            System.out.println();
            Main.logger.info("Config dateien erstellt!");
            Main.logger.info("Bitte configurieren sie die Konfigurationsdateien und starten sie Anschließend den Server neu!");
            System.exit(0);
        } else {
            if(ConfigHandler.getInstance().getConfig("system", "version").equals(Main.version)) {
                Main.logger.info("Sie verwenden die neuste Version: " + de.marko.radio.client.config.ConfigHandler.getInstance().getConfig("system", "version"));
                NetworkConfig.writeServerPort();
                MySQLConfig.getInstance().getSqlData();
                SQLHandler.getInstance().connect();

            } else {
                if(VersionController.getInstance().checkVersion()) {
                    System.out.println();
                    Main.logger.warn(" +-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+    ");
                    Main.logger.warn(" |O|l|d| |V|e|r|s|i|o|n| |D|e|t|e|c|t|e|d|!|    ");
                    Main.logger.warn(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                    Main.logger.warn(" |P|l|e|a|s|e| |U|p|d|a|t|e| |t|h|i|s| |A|p|p|!|");
                    Main.logger.warn(" +-+-+-+-+-+-+ +-+-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+");
                    System.out.println();
                    System.exit(0);
                }
            }
        }
    }

}
