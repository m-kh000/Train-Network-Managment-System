package ui.methods;

import javax.swing.*;
import java.awt.*;

public class ShowStations extends JPanel {
    public ShowStations() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new JLabel("Show Stations Page"), BorderLayout.CENTER);
    }
}