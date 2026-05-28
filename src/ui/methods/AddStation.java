package ui.methods;

import javax.swing.*;

import logic.Network;

import java.awt.*;
import ui.UI;

public class AddStation extends JPanel {
    public AddStation(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new JLabel("Add Station Page"), BorderLayout.CENTER);
    }
}