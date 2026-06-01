package ui.methods;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import logic.*;
import ui.Manager;
import ui.components.RouteComponent;
import ui.components.StationComponent;

public class GraphPage extends JPanel {

    // Model references
    private HashMap<Integer, Station> stations = null;

    // UI component maps
    HashMap<Integer, StationComponent> st_ui = new HashMap<>();
    // keyed by "fromId_toId"
    HashMap<String, RouteComponent> ro_ui = new HashMap<>();
    ArrayList<RouteComponent> shortestPathC = new ArrayList<>();
    ArrayList<RouteComponent> selectedRouteC = null;

    // Path data
    ArrayList<Route> shortestPath;

    // Appearance settings
    private Color stationColor;
    private Color roadColor = Color.BLACK;
    private Color shortestRoadColor = Color.BLUE;
    private int padding = 100;
    private int height = Manager.SCREEN_HEIGHT - 130;
    private int width = Manager.SCREEN_WIDTH - 180 - 20 - Manager.SIDE_SHORTEST;
    private int radius = Math.abs(Math.min(width, height) - padding) / 2;

    public GraphPage(Network network, Color stationColor, Color roadColor, Color shortestRoadColor, Color bg, ArrayList<Route> shortestPath) {
        this.shortestPath = shortestPath;
        this.stations = network.getStations();
        this.stationColor = stationColor;
        this.roadColor = roadColor;
        this.shortestRoadColor = shortestRoadColor;
        setPreferredSize(new Dimension(width, height));
        setBackground(Manager.ND_BG);
        fillData();
    }

    private void fillData() {
        double theta = 2 * Math.PI / stations.size();
        int x, y;
        int index = 0;
        for (Station s : stations.values()) {
            x = (int) (radius * Math.cos(theta * index) + 0.5 * (width - padding / 2));
            y = (int) (radius * Math.sin(theta * index) + 0.5 * (height - padding / 2)) + 5;
            if(shortestPath != null && (s.equals(shortestPath.getFirst().from) || s.equals(shortestPath.getLast().to)))
                st_ui.put(s.id, new StationComponent(s, x, y , true));
            else
                st_ui.put(s.id, new StationComponent(s, x, y));
            index++;
        }
        for (Station from : stations.values()) {
            StationComponent fromComp = st_ui.get(from.id);
            for (Route r : from.getRouts().values()) {
                StationComponent toComp = st_ui.get(r.to.id);
                ro_ui.put(from.id + "_" + r.to.id, new RouteComponent(fromComp, toComp, r));
                if (shortestPath != null && shortestPath.contains(r)) {
                    shortestPathC.add(ro_ui.get(r.from.id + "_" + r.to.id));
                    System.out.println("is from shortest path");
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (RouteComponent r : ro_ui.values()) {
            g.setColor(shortestPathC.contains(r) ? shortestRoadColor : roadColor);
            r.drawArrow(g);
        }

        g.setColor(stationColor);
        for (StationComponent s : st_ui.values()) {
            s.drawStation(g);
        }
    }
}
