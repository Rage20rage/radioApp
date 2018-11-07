package de.marko.radio.client.player;

import de.marko.radio.client.config.FavoritManager;
import de.marko.radio.client.gui.WindowManager;
import de.marko.radio.client.main.Main;
import de.marko.radio.client.network.Network;
import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class PlayerManager {

    Logger logger = de.marko.radio.server.main.Main.logger;

    private static PlayerManager playerManager;

    public static PlayerManager getInstance() {
        if(playerManager == null) {
            playerManager = new PlayerManager();
            playerManager.playerMode = PlayerMode.ALL;
        }
        return playerManager;
    }

    private Thread thread;
    private int playerPosition;

    private PlayerMode playerMode;

    public PlayerMode getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(PlayerMode playerMode) {
        this.playerMode = playerMode;
    }

    public void setPlayerPosition(int position) {
        logger.debug("Setze neue Radioposition: " + playerPosition);
        this.playerPosition = position;
        logger.debug("Neue Radioposition gesetzt!");
    }

    public int getPlayerPosition() {
        return this.playerPosition;
    }

    public void startNewPodcast(String url) {
        WindowManager.getInstance().setPauseButtonToIsPlaying();
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

    public boolean isPlaying() {
        logger.debug("Frage ab ob schon ein Radio läuft...");
        try {
            if(thread == null) {
                logger.debug("Das Radio läuft nicht!");
                return false;
            } else {
                if(thread.isAlive()) {
                    logger.debug("Das Radio läuft!");
                    return true;
                } else {
                    logger.debug("Das Radio läuft nicht!");
                    return false;
                }
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            logger.debug("Das Radio läuft nicht?");
            return false;
        }
    }

    public void stopRadioThread() {
        logger.debug("Das Radio wird gestoppt...");
        WindowManager.getInstance().setPauseButtonToIsPauseing();
        thread.stop();
        thread = null;
        logger.debug("Das Radio wurde gestoppt!");
    }

    public void playErrorSound() {
        logger.info("Spiele Error Sound...");
        String data = "/Error_Sound_Effect.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getClass().getResource(data).getFile()).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (LineUnavailableException e) {
            logger.error(e.getMessage());
        }
        logger.info("Error Sound gespielt!");
    }

    public void nextStation() {
        if(playerMode == PlayerMode.ALL) {
            logger.info("Wechsle zur nächsten Station...");
            if(PlayerManager.getInstance().getPlayerPosition() == Network.getInstance().stationNames.size()-1) {
                logger.debug("Am Ende der Radio-Liste angekommen! Gehe zum Anfang der Radio-Liste...");
                PlayerManager.getInstance().setPlayerPosition(0);
            } else {
                PlayerManager.getInstance().setPlayerPosition(PlayerManager.getInstance().getPlayerPosition()+1);
            }
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(PlayerManager.getInstance().getPlayerPosition()));
            logger.info("Zur nächsten Station gewächselt!");
        } else if(playerMode == PlayerMode.FAVORIT) {
            logger.info("Wechsle zur nächsten Station...");
            do {
                if(PlayerManager.getInstance().getPlayerPosition() == Network.getInstance().stationNames.size()-1) {
                    logger.debug("Am Ende der Radio-Liste angekommen! Gehe zum Anfang der Radio-Liste...");
                    PlayerManager.getInstance().setPlayerPosition(0);
                } else {
                    PlayerManager.getInstance().setPlayerPosition(PlayerManager.getInstance().getPlayerPosition()+1);
                }
            } while (!FavoritManager.getInstance().isAFavorit(Network.getInstance().stationNames.get(PlayerManager.getInstance().getPlayerPosition())));
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(PlayerManager.getInstance().getPlayerPosition()));
            logger.info("Zur nächsten Station gewächselt!");
        }
    }

    public void previousStation() {
        if(playerMode == PlayerMode.ALL) {
            logger.info("Wechsle zur vorhärigen Station...");
                if(PlayerManager.getInstance().getPlayerPosition() == 0) {
                    logger.debug("Am Anfang der Radio-Liste angekommen! Wechsle zum Ende der Radio-Liste...");
                    PlayerManager.getInstance().setPlayerPosition(Network.getInstance().stationNames.size()-1);
                } else {
                    PlayerManager.getInstance().setPlayerPosition(PlayerManager.getInstance().getPlayerPosition()-1);
                }
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(PlayerManager.getInstance().getPlayerPosition()));
            logger.info("Zur vorhärigen Station gewechselt!");
        } else if(playerMode == PlayerMode.FAVORIT) {
            logger.info("Wechsle zur vorhärigen Station...");
           do {
                if(PlayerManager.getInstance().getPlayerPosition() == 0) {
                    logger.debug("Am Anfang der Radio-Liste angekommen! Wechsle zum Ende der Radio-Liste...");
                    PlayerManager.getInstance().setPlayerPosition(Network.getInstance().stationNames.size()-1);
                } else {
                    PlayerManager.getInstance().setPlayerPosition(PlayerManager.getInstance().getPlayerPosition()-1);
                }
            }  while (!FavoritManager.getInstance().isAFavorit(Network.getInstance().stationNames.get(PlayerManager.getInstance().getPlayerPosition())));
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(PlayerManager.getInstance().getPlayerPosition()));
            logger.info("Zur vorhärigen Station gewechselt!");
        }
    }

    public void pauseStation() {
        logger.info("Pausiere das Radio...");
        if(!PlayerManager.getInstance().isPlaying() && PlayerManager.getInstance().getPlayerPosition() == -1) {
            logger.warn("Es wurde noch kein Radio ausgewählt! Schicke Warnmeldung an den User...");
            PlayerManager.getInstance().playErrorSound();
            JOptionPane.showMessageDialog(null, "Sie haben noch keinen Sender ausgewählt!", "Error-Senderauswahl", JOptionPane.WARNING_MESSAGE);
        } else if(!PlayerManager.getInstance().isPlaying() && PlayerManager.getInstance().getPlayerPosition() >= 0) {
            logger.info("Das Radio war schon pausiert! Starte den Radio-Stream...");
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(PlayerManager.getInstance().getPlayerPosition()));
        } else {
            WindowManager.getInstance().setPauseButtonToIsPauseing();
            Player.player.close();
            PlayerManager.getInstance().stopRadioThread();
            logger.info("Das Radio wurde pausiert!");
        }
    }

    public void stopStation() {
        logger.info("Das Radio wird gestoppt...");
        if (PlayerManager.getInstance().isPlaying() && PlayerManager.getInstance().getPlayerPosition() >= 0) {
            WindowManager.getInstance().setPauseButtonToIsPauseing();
            Player.player.close();
            PlayerManager.getInstance().stopRadioThread();
            logger.info("Das Radio wurde gestoppt!");
        } else {
            logger.warn("Es läuft kein Radio-Stream!");
        }
    }

}
