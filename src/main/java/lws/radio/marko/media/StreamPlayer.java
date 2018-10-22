package lws.radio.marko.media;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class StreamPlayer {

    public static void playStream(String urlStream) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(new URL(urlStream)).build();
            Response response = client.newCall(request).execute();
            InputStream inputStream = response.body().byteStream();
            Player player = new Player(inputStream);
            player.play();
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

}
