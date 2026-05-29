package ui.methods;

import javax.swing.*;

import logic.Network;
import ui.Manager;

import java.awt.*;

public class ShowStations extends JPanel {
    public ShowStations(Network network) {
        setLayout(new BorderLayout());
        add(Manager.topPanel("Map",network), BorderLayout.NORTH);
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL));

    }
}