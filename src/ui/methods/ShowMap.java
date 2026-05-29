package ui.methods;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import logic.*;
import ui.GraphPage;
import ui.Manager;
import ui.UI;

public class ShowMap extends JPanel {

    private static final Color BG_GRASS = new Color(162, 209, 73);
    private static final Color ROAD = new Color(100, 100, 100);
    private static final Color STATION = new Color(139, 69, 19);
    private static final Color SHORT_ROUTE = new Color(255, 0, 0);

    public ShowMap(Network network) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 90, 20, 90));
        
        JPanel map = new JPanel();
        // map.setBackground(BG_GRASS);
        map.setBorder(BorderFactory.createLineBorder(ROAD, 1));
        map.add(new GraphPage(network, STATION, ROAD, SHORT_ROUTE));

        add(Manager.topPanel("Map",network), BorderLayout.NORTH);
        add(map, BorderLayout.CENTER);

    }
}