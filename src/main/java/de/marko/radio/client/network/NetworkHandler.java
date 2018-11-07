package de.marko.radio.client.network;

import de.marko.radio.client.config.NetworkConfig;
import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class NetworkHandler {
    Logger logger = Main.logger;

    private Socket socket;

    public void connect() {
        logger.info("Verbindung zum Server wird Aufgabaut...");
        try {
            socket = new Socket(NetworkConfig.getConfig().getAddress(), NetworkConfig.getConfig().getPort());
            logger.info("Verbindung zum Server wurde hergestellt!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            logger.info("Verbindung zum Server wird geschlossen!");
            send("logout");
            try { Thread.sleep(100); } catch (Exception e) { Main.logger.error(e.getMessage());}
            socket.close();
            logger.info("Verbindung zum Server wurde geschlossen!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean isConnected() {
        if(socket == null) {
            return false;
        } else {
            return true;
        }
    }

    public void send(String message) {
        try {
            logger.debug("Inizialisiere das Senden...");
            OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
            logger.debug("Daten werden geschrieben");
            bufferedWriter.write(message);
            logger.info("Daten werden gesendet...");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            logger.info("Daten wurden gesendet: " + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String recive() {
        String message = "";
        try {
            logger.debug("Inizialisiere das Epfangen von Daten vom Server...");
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            logger.info("Warte auf Daten vom Server...");
            message = bufferedReader.readLine();
            logger.info("Daten vom Server erhalten: " + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if(message.isEmpty()) {
            logger.fatal("Fehlerhafte Antwort vom Server!");
        }
        return message;
    }

}
