package ui.methods;

import javax.swing.*;

import logic.Network;

import java.awt.*;
import ui.UI;

public class EditStation extends JPanel {
    public EditStation(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new JLabel("Edit Station Page"), BorderLayout.CENTER);
    }
}