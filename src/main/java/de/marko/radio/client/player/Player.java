package de.marko.radio.client.player;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.URL;

public class Player extends Thread {

    public static javazoom.jl.player.Player player;

    Logger logger = Logger.getRootLogger();

    String url;

    protected Player(String url) {
        this.url = url;
    }

    protected static Player playerClass;

    protected InputStream inputStream;
    protected Response response;
    protected Request request;
    protected OkHttpClient client;

    @Override
    public synchronized void run() {
        try {
            logger.debug("Inizialisiere Verbindung zum ausgewältem Radiosender...");
            client = new OkHttpClient();
            logger.info("Verbinde mit dem ausgewältem Radiosender...");
            request = new Request.Builder().url(new URL(url)).build();
            response = client.newCall(request).execute();
            logger.debug("Baue den Input-Stream auf...");
            inputStream = response.body().byteStream();
            player = new javazoom.jl.player.Player(inputStream);
            logger.info("Starte mit der Wiedergabe...");
            player.play();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
