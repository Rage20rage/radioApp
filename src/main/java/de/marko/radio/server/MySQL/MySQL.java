package de.marko.radio.server.MySQL;

import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQL {

    Logger logger = Main.logger;

    private static MySQL mySQL;

    public static MySQL getInstance() {
        if(mySQL == null) {
            mySQL = new MySQL();
        }
        return mySQL;
    }

    public void initSQL() {
        logger.info("Erstelle SQL-Tabelle falls nicht vorhanden....");
        SQLHandler.getInstance().executeQuerry("CREATE TABLE IF NOT EXISTS `RadioStations` " +
                "(`ID` int(255) NOT NULL AUTO_INCREMENT,`Name` VARCHAR(100) NOT NULL," +
                "`URL` VARCHAR(100) NOT NULL, PRIMARY KEY (`ID`)) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8");
        logger.info("Tabelle erstellt!");
    }


    public long countRadioStations() {
        logger.info("Hole Anzahl der Radiostationen...");
        long indexes = 0;
        ResultSet resultSet = SQLHandler.getInstance().getResultSet("SELECT `AUTO_INCREMENT` FROM information_schema.TABLES WHERE TABLE_NAME ='RadioStations'");
        try {
            resultSet.next();
            indexes = resultSet.getLong(1);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Es wurden " + (indexes-1) + " Radiostationen gezählt");
        return indexes-1;
    }

    public ArrayList<String> getAllRadioStationNames() {
        ArrayList<String> names = new ArrayList<>();
        ResultSet resultSet = SQLHandler.getInstance().getResultSet("SELECT * FROM `RadioStations`");
        try {
            for (int i = 0; resultSet.next(); i++) {
                names.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return names;
    }

    public ArrayList<String> getAllRadioStationURLs() {
        ArrayList<String> urls = new ArrayList<>();
        ResultSet resultSet = SQLHandler.getInstance().getResultSet("SELECT * FROM `RadioStations`");
        try {
            for (int i = 0; resultSet.next(); i++) {
                urls.add(resultSet.getString("URL"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return urls;
    }

}
