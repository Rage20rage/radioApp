package de.marko.radio.server.network;

import de.marko.radio.server.main.Main;

import java.io.IOException;

public class Sheduler extends Thread {

    @Override
    public void run() {
        try { Thread.sleep(10000); } catch (InterruptedException e) { Main.logger.error(e.getMessage()); }
        Main.logger.info("Schließe alle inaktiven Socket-Verbindungen...");
        int stoppedConnections = 0;
        for(Connection connection : NetworkHandler.getInstance().connections) {
            Main.logger.debug(connection.getIP() + " : Verbindungsstatus wird überprüft...");
            if(connection.socket.isClosed()) {
                Main.logger.debug(connection.getIP() + " : Client hat keine Verbindung mehr!");
                Main.logger.debug(connection.getIP() + " : Verbindung wird entfernt....");
                stoppedConnections++;
                NetworkHandler.getInstance().connections.remove(connection);
                try {
                    connection.socket.close();
                } catch (IOException e) {
                    Main.logger.error(e.getMessage());
                }
                connection.stop = true;
                Main.logger.debug(connection.getIP() + " : Verbindung wurde entfernt....");
            } else {
                Main.logger.debug(connection.getIP() + " : Client hat eine Verbindung zum Server!");
            }
        }
        Main.logger.info("Es wurde(n) " + stoppedConnections + " Verbindung(en) geschlossen!");
        Main.logger.info("Beende alle inaktiven Threads...");
        int stoppedThreads = 0;
        for (Thread thread : NetworkHandler.getInstance().threads) {
            Main.logger.debug("Ein Thread wir überprüft...");
            if(!thread.isAlive()) {
                Main.logger.debug("Ein Thread wird beendet...");
                stoppedThreads++;
                NetworkHandler.getInstance().threads.remove(thread);
                thread.stop();
                Main.logger.debug("Thread wurde beendet...");
            } else {
                Main.logger.debug("Thread läuft noch!");
            }
        }
        Main.logger.info("Es wurde(n) " + stoppedThreads + " Thread(s) geschlossen!");
    }

}
