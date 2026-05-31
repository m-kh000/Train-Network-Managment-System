package ui.methods;

import javax.swing.*;

import logic.Network;

import java.awt.*;

import ui.Manager;

public class EditRoute extends JPanel {
    public EditRoute(Network network) {
        setLayout(new BorderLayout());
        add(Manager.topPanel("Map",network), BorderLayout.NORTH);
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL));


    }
}