package de.marko.radio.client.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    protected MainWindow() {
        setTitle("Markos-Radio-Player");
        setLayout(new GridBagLayout());
        setSize(1000,600);
        gridBagConstraints = LayoutManager.getNewLayout();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
    }

    protected GridBagConstraints gridBagConstraints;
    private JPanel menuPanel = new JPanel();
    private JPanel stationListPanel = new JPanel();
    private JPanel audioSettingPanel = new JPanel();

    private void init() {
        menuPanel.setBackground(Color.GREEN);
        stationListPanel.setBackground(Color.BLUE);
        audioSettingPanel.setBackground(Color.RED);
        gridBagConstraints.weightx = 10;
        gridBagConstraints.weighty = 100;
        add(menuPanel, gridBagConstraints);
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 90;
        gridBagConstraints.weighty = 85;
        gridBagConstraints.gridx = 1;
        add(stationListPanel, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 90;
        gridBagConstraints.weighty = 15;
        gridBagConstraints.gridwidth = 2;
        add(audioSettingPanel, gridBagConstraints);
        menuPanel.show();
        stationListPanel.show();
        audioSettingPanel.show();
        show();
    }
}
