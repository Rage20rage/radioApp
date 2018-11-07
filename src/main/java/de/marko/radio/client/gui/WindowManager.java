package de.marko.radio.client.gui;

import de.marko.radio.client.main.Main;
import de.marko.radio.client.player.PlayerManager;
import de.marko.radio.client.player.PlayerMode;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class WindowManager {

    Logger logger = Main.logger;

    protected JButton pauseButton = new JButton();

    private static WindowManager windowManager;
    public static WindowManager getInstance() {
        if(windowManager == null) {
            windowManager = new WindowManager();
        }
        return windowManager;
    }

    MainWindow mainWindow;

    public void startMainWindow() {
        mainWindow = new MainWindow();
    }
private boolean settingPanelIsOpen = false;

    public void displayFavoritPanel() {
        logger.info("Wechsle zum FavortitenListPanel...");
        if(PlayerManager.getInstance().getPlayerMode() != PlayerMode.FAVORIT || settingPanelIsOpen) {
            mainWindow.setFavoritListPanel();
            mainWindow.cardPanel.repaint();
            CardLayout cardLayout = (CardLayout) mainWindow.cardPanel.getLayout();
            cardLayout.show(mainWindow.cardPanel, "favoritList");
            PlayerManager.getInstance().setPlayerMode(PlayerMode.FAVORIT);
        } else if(PlayerManager.getInstance().getPlayerMode() == PlayerMode.FAVORIT) {
            mainWindow.setFavoritListPanel();
            mainWindow.cardPanel.validate();
            mainWindow.cardPanel.repaint();
            CardLayout cardLayout = (CardLayout) mainWindow.cardPanel.getLayout();
            cardLayout.show(mainWindow.cardPanel, "favoritList");
        }
        settingPanelIsOpen = false;
        logger.info("Das Panel wurde gewechselt!");
    }

    public void displayHomePanel() {
        logger.info("Wechsle zum StationListPanel...");
        if(PlayerManager.getInstance().getPlayerMode() != PlayerMode.ALL || settingPanelIsOpen) {
            mainWindow.setStationListPanel();
            mainWindow.cardPanel.repaint();
            CardLayout cardLayout = (CardLayout) mainWindow.cardPanel.getLayout();
            cardLayout.show(mainWindow.cardPanel, "stationList");
            PlayerManager.getInstance().setPlayerMode(PlayerMode.ALL);
        }
        logger.info("Das Panel wurde gewechselt!");
        settingPanelIsOpen = false;
    }

    public void displaySettingPanel() {
        logger.info("Wechsle zum SettingPanel...");
        if(!settingPanelIsOpen) {
            mainWindow.setProgrammSettingPanel();
            mainWindow.cardPanel.repaint();
            CardLayout cardLayout = (CardLayout) mainWindow.cardPanel.getLayout();
            cardLayout.show(mainWindow.cardPanel, "settings");
            settingPanelIsOpen = true;
        }
        logger.info("Das Panel wurde gewechselt!");
    }

    public void setPauseButtonToIsPlaying() {
        logger.info("Setzte den Pause-Button auf auf den Play-Modus...");
        Image image = new ImageIcon(getClass().getResource("/media-pause.png")).getImage();
        image = image.getScaledInstance(100,80, java.awt.Image.SCALE_SMOOTH);
        pauseButton.setIcon(new ImageIcon(image));
        for (Component component : mainWindow.getComponents()) {
            if(component instanceof JPanel) {
                for (Component component2 : ((JPanel) component).getComponents()) {
                    if(component2 instanceof JButton) {
                        if(((JButton)component2).getName().equals("BUTTON-pause")) {
                            System.out.println("Button found!");
                            component2 = pauseButton;
                            component2.validate();
                            component2.repaint();
                            component2.show();
                        }
                    }
                }
                component.validate();
                component.repaint();
                component.show();
            }
        }
        mainWindow.validate();
        mainWindow.repaint();
        mainWindow.show();
        logger.info("Der Pause-Button wurde zum Play-Moodus geändert!");
    }

    public void setPauseButtonToIsPauseing() {
        logger.info("Setzte den Pause-Button auf auf den Pause-Modus...");
        Image image = new ImageIcon(getClass().getResource("/media-play.png")).getImage();
        image = image.getScaledInstance(100,80, java.awt.Image.SCALE_SMOOTH);
        pauseButton.setIcon(new ImageIcon(image));
        for (Component component : mainWindow.getComponents()) {
            if(component instanceof JPanel) {
                for (Component component2 : ((JPanel) component).getComponents()) {
                    if(component2 instanceof JButton) {
                        if(((JButton)component2).getName().equals("BUTTON-pause")) {
                            System.out.println("Button found!");
                            component2 = pauseButton;
                            component2.validate();
                            component2.repaint();
                            component2.show();
                        }
                    }
                }
                component.validate();
                component.repaint();
                component.show();
            }
        }
        mainWindow.validate();
        mainWindow.repaint();
        mainWindow.show();
        logger.info("Der Pause-Button wurde zum Pause-Moodus geändert!");
    }

}