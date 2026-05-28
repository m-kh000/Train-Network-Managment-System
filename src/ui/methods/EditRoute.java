package ui.methods;

import javax.swing.*;
import java.awt.*;
import ui.UI;

public class EditRoute extends JPanel {
    public EditRoute() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new JLabel("Edit Route Page"), BorderLayout.CENTER);
    }
}