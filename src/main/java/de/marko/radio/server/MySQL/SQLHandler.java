package de.marko.radio.server.MySQL;

import de.marko.radio.server.config.MySQLConfig;
import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler {

    Logger logger = Main.logger;

    private static SQLHandler sqlHandler;

    public static SQLHandler getInstance() {
        if(sqlHandler == null) {
            sqlHandler = new SQLHandler();
        }
        return sqlHandler;
    }

    private Connection connection;

    public void connect() {
        try {
            logger.info("Verbinde zur MySQL-Datenbank...");
            connection = DriverManager.getConnection("jdbc:mysql://" + MySQLConfig.getInstance().url + ":" +
                    MySQLConfig.getInstance().port + "/" + MySQLConfig.getInstance().database,
                    MySQLConfig.getInstance().user, MySQLConfig.getInstance().password);
            logger.info("Verbunden!");
            MySQL.getInstance().initSQL();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    protected void disconnect() {
        try {
            logger.info("Schließe MySQL-Verbindung...");
            connection.close();
            logger.info("Verbindung geschlossen!");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    protected void executeQuerry(String querry) {
        try {
            logger.info("Bereite SQL-Abfrage vor...");
            connection.prepareStatement(querry).executeQuery();
            logger.info("SQL-Anfrage durchgeführt!");
        } catch (SQLException e) {
            logger.warn("SQL-Anfrage Fehlgeschlagen!");
            disconnect();
            logger.warn("Verbindung zu Datenbank wird zurückgesetzt...");
            connection = null;
            logger.warn("Verbinde erneut mit der Datenbank...");
            connect();
            try {
                logger.info("Bereite erneute SQL-Abfrage vor...");
                connection.prepareStatement(querry).executeQuery();
                logger.info("SQL-Anfrage durchgeführt!");
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
                logger.fatal("Verbindungen mit der Datenbank sind nicht möglich!");
            }
        }
    }

    protected ResultSet getResultSet(String querry) {
        try {
            logger.info("Bereite SQL-Abfrage vor...");
            ResultSet resultSet = connection.prepareStatement(querry).executeQuery();
            logger.info("SQL-Anfrage durchgeführt!");
            return resultSet;
        } catch (SQLException e) {
            logger.warn("SQL-Anfrage Fehlgeschlagen!");
            disconnect();
            logger.warn("Verbindung zu Datenbank wird zurückgesetzt...");
            connection = null;
            logger.warn("Verbinde erneut mit der Datenbank...");
            connect();
            try {
                logger.info("Bereite erneute SQL-Abfrage vor...");
                ResultSet resultSet = connection.prepareStatement(querry).executeQuery();
                logger.info("SQL-Anfrage durchgeführt!");
                return resultSet;
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
                logger.fatal("Verbindungen mit der Datenbank sind nicht möglich!");
            }
        }
        return null;
    }

}
