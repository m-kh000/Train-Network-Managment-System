package ui.methods;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import logic.*;
import ui.GraphPage;
import ui.Manager;

public class ShowMap extends JPanel {

    private static final Color BG_GRASS = Color.WHITE;
    private static final Color ROAD = new Color(30, 30, 30);
    private static final Color STATION = new Color(250, 200, 90);
    private static final Color SHORT_ROUTE = new Color(255, 0, 0);

    private final JPanel map;
    private final JPanel sideShortest;
    private final Network network;

    public ShowMap(Network network) {
        this.network = network;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 90, 20, 90));
        
        map = new JPanel(new BorderLayout());
        sideShortest = new JPanel(new BorderLayout());

        map.setBorder(BorderFactory.createLineBorder(ROAD, 1));
        map.add(new GraphPage(network, STATION, ROAD, SHORT_ROUTE, BG_GRASS, null));
        sideShortest.setBorder(BorderFactory.createLineBorder(ROAD, 1));
        sideShortest.add(new Shortest(network, this));

        add(Manager.topPanel("Map", network), BorderLayout.NORTH);
        add(map, BorderLayout.WEST);
        add(sideShortest, BorderLayout.EAST);

    }

    public void updateShortestPath(ArrayList<Route> shortest) {
        System.out.println("updating");
        map.removeAll();
        map.add(new GraphPage(network, STATION, ROAD, SHORT_ROUTE, BG_GRASS,shortest));
        revalidate();
        repaint();
    }
}