package de.marko.radio.client.gui;

import java.awt.*;

public class LayoutManager {

    protected static GridBagConstraints getNewLayout() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        return gridBagConstraints;
    }

}
