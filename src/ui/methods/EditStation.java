package ui.methods;

import javax.swing.*;
import java.awt.*;
import ui.UI;

public class EditStation extends JPanel {
    public EditStation() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new JLabel("Edit Station Page"), BorderLayout.CENTER);
    }
}