package ui.methods;

import javax.swing.*;

import logic.Network;

import java.awt.*;
import ui.UI;

public class EditRoute extends JPanel {
    public EditRoute(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new JLabel("Edit Route Page"), BorderLayout.CENTER);
    }
}