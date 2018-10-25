package de.marko.radio.server.network;

import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkHandler {

    Logger logger = Main.logger;

    private static NetworkHandler networkHandler;

    public static NetworkHandler getInstance() {
        if(networkHandler == null) {
            networkHandler = new NetworkHandler();
        }
        return networkHandler;
    }

    public List<Connection> connections = new ArrayList<>();
    public List<Thread> threads = new ArrayList<>();

    private ServerSocket serverSocket = null;
    private Socket socket;

    public int port;

    public void connenct() {
        try {
            if(serverSocket == null) {
                logger.info("Starte ServerSocket...");
                serverSocket = new ServerSocket(port);
                logger.info("ServerSocket hört auf den Port " + port + "!");
            }
            logger.info("Warte auf eingehende Verbindung...");
            socket = serverSocket.accept();
            Connection connection = new Connection(socket);
            Thread thread = new Thread(connection);
            thread.start();
            threads.add(thread);
            connections.add(connection);
            logger.info(connection.getIP() + " : Client hat sich verbunden!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void disconnect(Connection connection) {
        logger.info(connection.getIP() + " : Verbindung wird getrennt...");
        connection.stop = true;
        try {
            connection.socket.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info(connection.getIP() + " : Verbindung wurde getrennt!");
    }

    public void send(Connection connection, String msg) {
        try {
            logger.debug(connection.getIP() + " : Inizialisiere das Senden...");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            logger.debug(connection.getIP() + " : Daten werden geschrieben");
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            logger.info(connection.getIP() + " : Daten werden gesendet...");
            bufferedWriter.flush();
            logger.info(connection.getIP() + " : Daten wurden gesendet!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String recive(Connection connection) {
        String msg = "";
        try {
            logger.debug(connection.getIP() + " : Inizialisiere das Epfangen von Daten vom Client...");
            InputStreamReader inputStreamReader = new InputStreamReader(connection.socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            logger.info(connection.getIP() + " : Warte auf Daten vom Client...");
            msg = bufferedReader.readLine();
            logger.info(connection.getIP() + " : Daten vom Client erhalten!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if(msg == null) {
            logger.fatal(connection.getIP() + " : Fehlerhafte Antwort vom Client!");
        }
        return msg;
    }

}
