package ui.methods;

import javax.swing.*;
import java.awt.*;
import ui.UI;

public class AddStation extends JPanel {
    public AddStation() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new JLabel("Add Station Page"), BorderLayout.CENTER);
    }
}