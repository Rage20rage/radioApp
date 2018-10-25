package de.marko.radio.server.network;

import de.marko.radio.server.Actions.RadioAction;

import java.io.IOException;

public class Network {

    private static Network network;

    public static Network getInstance() {
        if(network == null) {
            network = new Network();
        }
        return network;
    }

    public void execute(Connection connection, String message) {
        if(message.equals("logout")) {
            try {
                connection.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(message.startsWith("Radio-")) {
            RadioAction.executeAction(connection, message);
        }
    }

}
