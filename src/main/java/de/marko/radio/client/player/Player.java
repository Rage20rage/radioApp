package de.marko.radio.client.player;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;
import java.net.URL;

public class Player {
    private static Player ourInstance = new Player();

    public static Player getInstance() {
        return ourInstance;
    }

    public void play(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(new URL(url)).build();
            Response response = client.newCall(request).execute();
            InputStream inputStream = response.body().byteStream();
            javazoom.jl.player.Player player = new javazoom.jl.player.Player(inputStream);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
