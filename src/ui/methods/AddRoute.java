package ui.methods;

import javax.swing.*;
import java.awt.*;
import ui.UI;

public class AddRoute extends JPanel {
    public AddRoute() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new JLabel("Add Route Page"), BorderLayout.CENTER);
    }
}