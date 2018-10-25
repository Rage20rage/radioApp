package de.marko.radio.server.config;

import de.marko.radio.server.main.Main;

public class MySQLConfig {

    private static MySQLConfig mySQLConfig;

    public static MySQLConfig getInstance() {
        if(mySQLConfig == null) {
            mySQLConfig = new MySQLConfig();
        }
        return mySQLConfig;
    }

    public String url;
    public String port;
    public String database;
    public String user;
    public String password;

    public void getSqlData() {
        Main.logger.debug("Speichere SQL-Daten im Cache...");
        url = ConfigHandler.getInstance().getConfig("sql", "url");
        port = ConfigHandler.getInstance().getConfig("sql", "port");
        database = ConfigHandler.getInstance().getConfig("sql", "database");
        user = ConfigHandler.getInstance().getConfig("sql", "user");
        password = ConfigHandler.getInstance().getConfig("sql", "passwort");
    }

}
