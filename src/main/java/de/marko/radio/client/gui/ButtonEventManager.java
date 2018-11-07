package de.marko.radio.client.gui;

import de.marko.radio.client.config.FavoritManager;
import de.marko.radio.client.main.Main;
import de.marko.radio.client.network.Network;
import de.marko.radio.client.player.PlayerManager;
import de.marko.radio.client.player.PlayerMode;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEventManager implements ActionListener {

    Logger logger = Main.logger;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        logger.info("Ein Knopf im Programm wurde gedrückt!");
        logger.debug("Frage ab welche Instanz der Knopf hat...");
        if(actionEvent.getSource() instanceof JButton) {
            logger.debug("Der Knopf ist ein Normaler Button!");
            logger.debug("Hole den Namen vom Button...");
            String buttonName = ((JButton) actionEvent.getSource()).getName();
            logger.debug("Der Knopf " + buttonName + " wurde gedrückt!");
            logger.debug("Verweise zum Richtigen Event...");
            if(Network.getInstance().stationNames.contains(buttonName)) {
                radioButtonAction(buttonName);
            } else if(buttonName.startsWith("BUTTON-")) {
                menuControlButtonAction(buttonName);
            } else if(buttonName.startsWith("MENU-")) {
                menuChooserButtonAction(buttonName);
            }
        } else if(actionEvent.getSource() instanceof JToggleButton) {
            logger.debug("Der Knopf ist ein ToggleButton!");
            logger.debug("Überprüfe ob der Knopf angewählt wurde...");
            boolean isButtonSelectet = ((JToggleButton) actionEvent.getSource()).isSelected();
            logger.debug("Hole den Namen vom Button...");
            String buttonName = ((JToggleButton) actionEvent.getSource()).getName();
            logger.debug("Der Knopf " + buttonName + " wurde gedrückt!");
            if(isButtonSelectet) {
                FavoritManager.getInstance().putInFavorits(buttonName);
            } else {
                FavoritManager.getInstance().removeFromFavorit(buttonName);
            }
            if(PlayerManager.getInstance().getPlayerMode() == PlayerMode.FAVORIT) {
                WindowManager.getInstance().displayFavoritPanel();
            }
        }
    }

    private void menuChooserButtonAction(String buttonName) {
        if(buttonName.equals("MENU-Home")) {
            WindowManager.getInstance().displayHomePanel();
        } else if(buttonName.equals("MENU-Favorit")) {
            WindowManager.getInstance().displayFavoritPanel();
        } else if(buttonName.equals("MENU-Settings")) {
            WindowManager.getInstance().displaySettingPanel();
        }
    }

    private void menuControlButtonAction(String name) {
        if(name.equals("BUTTON-previous")) {
            PlayerManager.getInstance().previousStation();
        } else if(name.equals("BUTTON-pause")) {
            PlayerManager.getInstance().pauseStation();
        } else if(name.equals("BUTTON-next")) {
            PlayerManager.getInstance().nextStation();
        } else {
            logger.error("Dieser Button existiert nicht!");
        }
    }

    private void radioButtonAction(String name) {
        boolean searchButton = true;
        int radioPositon = 0;
        for (int i = 0; i < Network.getInstance().stationNames.size() && searchButton; i++) {
            if(Network.getInstance().stationNames.get(i).equals(name)) {
                searchButton = false;
                radioPositon = i;
            }
        }
        if(searchButton) {
            logger.error("Dieser Radiostream existiert nicht!");
        } else {
            PlayerManager.getInstance().startNewPodcast(Network.getInstance().stationURLs.get(radioPositon));
            PlayerManager.getInstance().setPlayerPosition(radioPositon);
        }
    }

}
