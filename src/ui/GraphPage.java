package ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import logic.*;
import ui.components.RouteComponent;
import ui.components.StationComponent;

public class GraphPage extends JPanel {

    HashMap<Integer, Station> stations = null;

    ArrayList<RouteComponent> selectedRoute = null;

    HashMap<Integer, StationComponent> st_ui = new HashMap<>();
    // keyed by "fromId_toId"
    HashMap<String, RouteComponent> ro_ui = new HashMap<>();
    ArrayList<RouteComponent> shortestPath = new ArrayList<>();
    private Color stationColor;
    private Color roadColor;
    private Color shortestRoadColor;
    private Color bg;
    private int padding = 100;
    private int height = Manager.SCREEN_HEIGHT-130 , width = Manager.SCREEN_WIDTH-180;
    private int radius = Math.abs(Math.min(width,height) - padding)/2;

    public GraphPage(Network network, Color stationColor, Color roadColor, Color shortestRoadColor, Color bg) {
        this.stations = network.getStations();
        System.out.println(stations);
        this.stationColor = stationColor;
        this.roadColor = roadColor;
        this.shortestRoadColor = shortestRoadColor;
        this.bg = bg;
        setSize(new Dimension(width,height));
        setBackground(bg);
        fillData();
    }

    private void fillData() {
        double theta = 2 * Math.PI / stations.size() ;
        int x , y;
        int index = 0;
        for (Station s : stations.values()) {
            x = (int)(radius * Math.cos(theta * index) + 0.5 * (width - padding/2));
            y = (int)(radius * Math.sin(theta * index) + 0.5 * (height - padding/2));
            st_ui.put(s.id, new StationComponent(s, x, y));
            index++;
        }
        for (Station from : stations.values()) {
            StationComponent fromComp = st_ui.get(from.id);
            for (Route r : from.getRouts().values()) {
                StationComponent toComp = st_ui.get(r.to.id);
                ro_ui.put(from.id + "_" + r.to.id, new RouteComponent(fromComp, toComp, r));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (RouteComponent r : ro_ui.values()) {
            g.setColor(shortestPath.contains(r) ? (shortestRoadColor != null ? shortestRoadColor : Color.RED)
                    : (roadColor != null ? roadColor : Color.BLACK));
            r.drawArrow(g);
        }

        g.setColor(stationColor != null ? stationColor : Color.BLUE);
        for (StationComponent s : st_ui.values()) {
            s.drawStation(g);
        }
    }
}
