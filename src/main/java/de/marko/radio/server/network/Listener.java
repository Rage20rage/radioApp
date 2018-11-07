package de.marko.radio.server.network;

public class Listener {

    public static void waitForConnection() {
        NetworkHandler.getInstance().connenct();
    }

}
