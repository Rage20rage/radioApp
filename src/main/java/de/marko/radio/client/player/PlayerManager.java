package de.marko.radio.client.player;

import de.marko.radio.client.main.Main;

public class PlayerManager {

    private static PlayerManager playerManager;

    public static PlayerManager getInstance() {
        if(playerManager == null) {
            playerManager = new PlayerManager();
        }
        return playerManager;
    }

    private Thread thread;

    public void startNewPodcast(String url) {
        if(thread == null) {
            Main.logger.info("Starte den Radio-Stream...");
            thread = new Thread(new Player(url));
            thread.start();
            Main.logger.info("Stream gestartet!");
        } else {
            Main.logger.info("Stoppe den alten Radio-Stream");
            thread.stop();
            Main.logger.info("Starte den Radio-Stream...");
            thread = new Thread(new Player(url));
            thread.start();
            Main.logger.info("Stream gestartet!");
        }
    }

}
