package ui.methods;

import javax.swing.*;
import java.awt.*;
import logic.*;
import ui.UI;

public class ShowMap extends JPanel {
    public ShowMap(Network network) {
        setLayout(new BorderLayout());
        add(UI.backBtn(network), BorderLayout.NORTH);
        add(new ui.GraphPage(network.getStations()), BorderLayout.CENTER);
    }
}