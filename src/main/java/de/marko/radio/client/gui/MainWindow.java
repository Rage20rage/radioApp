package de.marko.radio.client.gui;

import de.marko.radio.client.config.FavoritManager;
import de.marko.radio.client.main.Main;
import de.marko.radio.client.network.Network;
import de.marko.radio.client.player.PlayerManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    Logger logger = Main.logger;

    private MainWindow mainWindow;

    public MainWindow getInstance() {
        if(mainWindow == null) {
            mainWindow = new MainWindow();
        }
        return mainWindow;
    }

    protected MainWindow() {
        logger.info("Baue das Fenster...");
        setTitle("Markos-Radio-Player");
        setLayout(new GridBagLayout());
        setSize(1000,600);
        gridBagConstraints = LayoutManager.getNewLayout();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scrollPane = new JScrollPane(cardPanel);
        setStationListPanelToScrollable();
        init();
    }

    protected GridBagConstraints gridBagConstraints;
    private GridBagConstraints frameGridBagContraints;
    private JPanel menuPanel = new JPanel(new GridBagLayout());
    private JPanel audioSettingsPanel = new JPanel(new GridBagLayout());
    protected JPanel stationListPanel = new JPanel(new SpringLayout());
    protected JPanel favoritListPanel = new JPanel(new SpringLayout());
    protected JPanel programmSettingPanel = new JPanel();
    protected JPanel cardPanel = new JPanel(new CardLayout());
    protected JScrollPane scrollPane;
    private JLabel  isPlaying = new JLabel();

    protected void init() {
        logger.debug("Inizialisiere das Fenster...");
        logger.debug("Setzte alle Layouts auf die Fenster...");
        gridBagConstraints.weightx = 5;
        gridBagConstraints.weighty = 100;
        add(menuPanel, gridBagConstraints);

        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 90;
        gridBagConstraints.weighty = 15;
        gridBagConstraints.gridwidth = 2;
        add(audioSettingsPanel, gridBagConstraints);

        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 95;
        gridBagConstraints.weighty = 95;
        gridBagConstraints.gridx = 1;
        add(scrollPane, gridBagConstraints);

        frameGridBagContraints = gridBagConstraints;

        logger.debug("Inizialisiere alle Panel...");
        setMenuPanel();
        setStationListPanel();
        setAudioSettingPanel();
        setFavoritListPanel();
        setProgrammSettingPanel();
        setCradPanel();

        logger.debug("Zeige alle Fenster...");
        menuPanel.show();
        stationListPanel.show();
        scrollPane.show();
        audioSettingsPanel.show();
        show();
    }

    private void setAudioSettingPanel() {
        logger.debug("Baue das AudioSettingsPanel...");
        gridBagConstraints = LayoutManager.getNewLayout();
        JButton previousButton = new JButton();
        JButton nextButton = new JButton();

        logger.debug("Setzte alle Bilder...");
        Image image = new ImageIcon(getClass().getResource("/media-previous.png")).getImage();
        image = image.getScaledInstance(100,80, java.awt.Image.SCALE_SMOOTH);
        previousButton.setIcon(new ImageIcon(image));
        image = new ImageIcon(getClass().getResource("/media-play.png")).getImage();
        image = image.getScaledInstance(100,80, java.awt.Image.SCALE_SMOOTH);
        WindowManager.getInstance().pauseButton.setIcon(new ImageIcon(image));
        image = new ImageIcon(getClass().getResource("/media-next.png")).getImage();
        image = image.getScaledInstance(100,80, java.awt.Image.SCALE_SMOOTH);
        nextButton.setIcon(new ImageIcon(image));

        logger.debug("Setzte alle SourceNamen...");
        previousButton.setName("BUTTON-previous");
        WindowManager.getInstance().pauseButton.setName("BUTTON-pause");
        nextButton.setName("BUTTON-next");

        logger.debug("Füge ActionListener hinzu...");
        previousButton.addActionListener(new ButtonEventManager());
        WindowManager.getInstance().pauseButton.addActionListener(new ButtonEventManager());
        nextButton.addActionListener(new ButtonEventManager());

        logger.debug("Füge die Sources dem dazugehörigen Panel hinzu...");
        audioSettingsPanel.add(previousButton, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        audioSettingsPanel.add(WindowManager.getInstance().pauseButton, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        audioSettingsPanel.add(nextButton, gridBagConstraints);

        logger.debug("Zeige alle Sources...");
        previousButton.show();
        WindowManager.getInstance().pauseButton.show();
        nextButton.show();
    }

    private void setMenuPanel() {
        logger.debug("Baue das MenuPanel...");
        JButton homeButton = new JButton("Home");
        JButton favoritButton = new JButton("Favoriten");
        JButton ownStationButton = new JButton("Eigenes Radio hinzufügen");
        JButton settingsButton = new JButton("Einstellungen");
        JLabel spacer = new JLabel("");

        logger.debug("Setzte alle SourceNamen...");
        homeButton.setName("MENU-Home");
        favoritButton.setName("MENU-Favorit");
        ownStationButton.setName("MENU-");
        settingsButton.setName("MENU-Settings");

        logger.debug("Setzte alle Bilder...");
        Image image = new ImageIcon(getClass().getResource("/home.png")).getImage();
        image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
        homeButton.setIcon(new ImageIcon(image));

        image = new ImageIcon(getClass().getResource("/star.png")).getImage();
        image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
        favoritButton.setIcon(new ImageIcon(image));

        image = new ImageIcon(getClass().getResource("/add.png")).getImage();
        image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
        ownStationButton.setIcon(new ImageIcon(image));

        image = new ImageIcon(getClass().getResource("/settings.png")).getImage();
        image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
        settingsButton.setIcon(new ImageIcon(image));

        logger.debug("Füge die Sources dem dazugehörigen Panel hinzu...");
        gridBagConstraints = LayoutManager.getNewLayout();
        menuPanel.add(homeButton, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        menuPanel.add(favoritButton, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        //menuPanel.add(ownStationButton, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        menuPanel.add(settingsButton, gridBagConstraints);
        gridBagConstraints.gridy = 19;
        gridBagConstraints.weighty = 40;
        menuPanel.add(spacer, gridBagConstraints);
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridy = 20;
        menuPanel.add(isPlaying, gridBagConstraints);

        setIsPlayingLabel();

        logger.debug("Füge ActionListener hinzu...");
        homeButton.addActionListener(new ButtonEventManager());
        favoritButton.addActionListener(new ButtonEventManager());
        settingsButton.addActionListener(new ButtonEventManager());

        logger.debug("Zeige alle Sources...");
        homeButton.show();
        favoritButton.show();
        //ownStationButton.show();
        settingsButton.show();
        isPlaying.show();
    }

    protected void setStationListPanel() {
        logger.debug("Baue das StationListPanel...");
        stationListPanel.removeAll();
        int i = 0;
        for(String stationName : Network.getInstance().stationNames) {
            JLabel placeHolder = new JLabel();
            JLabel label = new JLabel();
            JButton playButton = new JButton();
            JToggleButton favButton = new JToggleButton();

            logger.debug("Setzte alle SourceNamen...");
            favButton.setName(stationName);
            playButton.setName(stationName);

            placeHolder.setText("     ");
            label.setText(stationName);

            logger.debug("Setzte alle Bilder...");
            Image image = new ImageIcon(getClass().getResource("/media-play.png")).getImage();
            image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
            playButton.setIcon(new ImageIcon(image));
            playButton.setText("Starte Stream");
            image = new ImageIcon(getClass().getResource("/star.png")).getImage();
            image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
            favButton.setIcon(new ImageIcon(image));
            favButton.setText("Favoriten");

            if(FavoritManager.getInstance().isAFavorit(stationName)) {
                favButton.setSelected(true);
            }

            logger.debug("Füge ActionListener hinzu...");
            playButton.addActionListener(new ButtonEventManager());
            favButton.addActionListener(new ButtonEventManager());

            logger.debug("Füge die Sources dem dazugehörigen Panel hinzu...");
            stationListPanel.add(placeHolder);
            stationListPanel.add(label);
            stationListPanel.add(playButton);
            stationListPanel.add(favButton);

            logger.debug("Zeige alle Sources...");
            label.show();
            playButton.show();
            favButton.show();
            i++;
        }
        SpringUtilities.makeCompactGrid(stationListPanel, i, 4, 1,1,10,10);
    }

    protected void setFavoritListPanel() {
        logger.debug("Baue das FavoritListPanel...");
        favoritListPanel.removeAll();
        int i = 0;
        for(String stationName : Network.getInstance().stationNames) {
            if(FavoritManager.getInstance().isAFavorit(stationName)) {
                JLabel placeHolder = new JLabel();
                JLabel label = new JLabel();
                JButton playButton = new JButton();
                JToggleButton favButton = new JToggleButton();

                logger.debug("Setzte alle SourceNamen...");
                favButton.setName(stationName);
                playButton.setName(stationName);

                placeHolder.setText("     ");
                label.setText(stationName);

                logger.debug("Setzte alle Bilder...");
                Image image = new ImageIcon(getClass().getResource("/media-play.png")).getImage();
                image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
                playButton.setIcon(new ImageIcon(image));
                playButton.setText("Starte Stream");
                image = new ImageIcon(getClass().getResource("/star.png")).getImage();
                image = image.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);
                favButton.setIcon(new ImageIcon(image));
                favButton.setText("Favoriten");

                favButton.setSelected(true);

                logger.debug("Füge ActionListener hinzu...");
                playButton.addActionListener(new ButtonEventManager());
                favButton.addActionListener(new ButtonEventManager());

                logger.debug("Füge die Sources dem dazugehörigen Panel hinzu...");
                favoritListPanel.add(placeHolder);
                favoritListPanel.add(label);
                favoritListPanel.add(playButton);
                favoritListPanel.add(favButton);

                logger.debug("Zeige alle Sources...");
                label.show();
                playButton.show();
                favButton.show();
                i++;
            }
        }
        SpringUtilities.makeCompactGrid(favoritListPanel, i, 4, 1,1,10,10);
    }

    protected void setProgrammSettingPanel() {
        logger.debug("Baue das ProgrammSettingPanel...");
        programmSettingPanel.removeAll();
        JLabel jLabel = new JLabel("Zurzeit sind keine Einstellungen Verfügbar!");

        logger.debug("Setzte alle SourceNamen...");

        logger.debug("Füge die Sources dem dazugehörigen Panel hinzu...");
        programmSettingPanel.add(jLabel);

        logger.debug("Zeige alle Sources...");
        jLabel.show();
        //programmSettingPanel.show();
    }

    private void setCradPanel() {
        cardPanel.add(stationListPanel, "stationList");
        cardPanel.add(favoritListPanel, "favoritList");
        cardPanel.add(programmSettingPanel, "settings");
    }

    private void setStationListPanelToScrollable() {
        logger.debug("Mache das ScrollPane Scrollbar...");
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void setIsPlayingLabel() {
        logger.debug("Inizialisiere das isPlayingLabel als neuen Thread...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                    if(PlayerManager.getInstance().getPlayerPosition() == -1 && !PlayerManager.getInstance().isPlaying()) {
                        isPlaying.setText("Zurzeit wird gespielt: "+
                                "\n----------");
                    } else {
                        isPlaying.setText("Zurzeit wird gespielt: " +
                                "\n" + Network.getInstance().stationNames.get(PlayerManager.getInstance().getPlayerPosition()));
                    }
                }
            }
        });
        logger.debug("Starte den neuen Thread...");
        thread.start();
        logger.debug("Thread gestartet!");
    }

}
