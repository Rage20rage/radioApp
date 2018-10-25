package de.marko.radio.server.network;

import java.net.Socket;

public class Connection extends Thread {

    public Socket socket;
    public boolean stop = false;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        while (!stop) {
           String message = NetworkHandler.getInstance().recive(this);
            Network.getInstance().execute(this, message);
        }
    }

}
