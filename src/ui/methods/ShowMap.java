package ui.methods;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import logic.*;
import ui.GraphPage;
import ui.Manager;
import ui.UI;

public class ShowMap extends JPanel {

    private static final Color BG_GRASS = Color.WHITE;
    private static final Color ROAD = new Color(30, 30, 30);
    private static final Color STATION = new Color(250, 200, 90);
    private static final Color SHORT_ROUTE = new Color(255, 0, 0);

    public ShowMap(Network network) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 90, 20, 90));
        
        JPanel map = new JPanel(new BorderLayout());
        map.setBorder(BorderFactory.createLineBorder(ROAD, 1));
        map.add(new GraphPage(network, STATION, ROAD, SHORT_ROUTE, BG_GRASS));

        add(Manager.topPanel("Map",network), BorderLayout.NORTH);
        add(map, BorderLayout.CENTER);

    }
}