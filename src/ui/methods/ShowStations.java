package ui.methods;

import javax.swing.*;

import logic.Network;
import ui.UI;

import java.awt.*;

public class ShowStations extends JPanel {
    public ShowStations(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new JLabel("Show Stations Page"), BorderLayout.CENTER);
    }
}