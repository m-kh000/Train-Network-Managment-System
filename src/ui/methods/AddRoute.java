package ui.methods;

import javax.swing.*;

import logic.Network;

import java.awt.*;
import ui.UI;

public class AddRoute extends JPanel {
    public AddRoute(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new JLabel("Add Route Page"), BorderLayout.CENTER);
    }
}