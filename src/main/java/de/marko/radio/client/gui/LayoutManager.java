package de.marko.radio.client.gui;

import de.marko.radio.client.main.Main;
import org.apache.log4j.Logger;

import java.awt.*;

public class LayoutManager {

    private static Logger logger = Main.logger;

    protected static GridBagConstraints getNewLayout() {
        logger.debug("Erstelle ein neues Layout...");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        logger.debug("Neues Layout erstell!");
        return gridBagConstraints;
    }

    protected static GridBagConstraints getScrollPanelLayout(java.awt.LayoutManager layoutManager) {
        GridBagConstraints gridBagConstraints = getNewLayout();
        logger.debug("Passe das Layout für das ScrollPanel an...");
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 95;
        gridBagConstraints.weighty = 95;
        gridBagConstraints.gridx = 1;
        logger.debug("Layout angepasst!");
        return gridBagConstraints;
    }

}
