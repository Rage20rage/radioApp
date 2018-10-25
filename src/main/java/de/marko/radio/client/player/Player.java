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

    public Player(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            logger.debug("Inizialisiere Verbindung zum ausgewältem Radiosender...");
            OkHttpClient client = new OkHttpClient();
            logger.info("Verbinde mit dem ausgewältem Radiosender...");
            Request request = new Request.Builder().url(new URL(url)).build();
            Response response = client.newCall(request).execute();
            logger.debug("Baue den Input-Stream auf...");
            InputStream inputStream = response.body().byteStream();
            player = new javazoom.jl.player.Player(inputStream);
            logger.info("Starte mit der Wiedergabe...");
            player.play();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
