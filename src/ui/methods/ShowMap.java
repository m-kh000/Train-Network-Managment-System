package ui.methods;

import javax.swing.*;
import java.awt.*;
import logic.*;

public class ShowMap extends JPanel {
    public ShowMap() {
        setLayout(new BorderLayout());
        add(UI.backBtn(), BorderLayout.NORTH);
        add(new ui.GraphPage(App.getNetwork().getStations()), BorderLayout.CENTER);
    }
}