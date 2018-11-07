package de.marko.radio.client.listener;

import de.marko.radio.client.main.Main;
import de.marko.radio.client.player.PlayerManager;
import org.apache.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {

    Logger logger = Main.logger;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if(nativeKeyEvent.getRawCode() == 65300) {
            logger.debug("Die Pause-Taste an der Tastatur wurde gedrückt!");
            PlayerManager.getInstance().pauseStation();
        } else if(nativeKeyEvent.getRawCode() == 65301) {
            logger.debug("Die Stop-Taste an der Tastatur wurde gedrückt!");
            PlayerManager.getInstance().stopStation();
        } else if(nativeKeyEvent.getRawCode() == 65302) {
            logger.debug("Die Vorher-Taste an der Tastatur wurde gedrückt!");
            PlayerManager.getInstance().previousStation();
        } else if(nativeKeyEvent.getRawCode() == 65303) {
            logger.debug("Die Weiter-Taste an der Tastatur wurde gedrückt!");
            PlayerManager.getInstance().nextStation();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
