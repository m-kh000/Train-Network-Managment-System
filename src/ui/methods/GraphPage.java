package ui.methods;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Network;
import logic.Route;
import logic.Station;
import ui.Manager;
import ui.components.RouteComponent;
import ui.components.StationComponent;

public class GraphPage extends JPanel {

    private final Network network;
    private final HashMap<Integer, Station> stations;
    private final HashMap<Integer, StationComponent> stationComponents = new HashMap<>();
    private final HashMap<String, RouteComponent> routeComponents = new HashMap<>();
    private final ArrayList<RouteComponent> shortestPathComponents = new ArrayList<>();
    private final ArrayList<Route> shortestPath;

    private final Color stationColor;
    private final Color roadColor;
    private final Color shortestRoadColor;
    private final int padding = 100;
    private final int height = Manager.SCREEN_HEIGHT - 130;
    private final int width = Manager.SCREEN_WIDTH - 180 - 20 - Manager.SIDE_SHORTEST;
    private final int radius = Math.abs(Math.min(width, height) - padding) / 2;

    public GraphPage(Network network, Color stationColor, Color roadColor, Color shortestRoadColor, Color bg, ArrayList<Route> shortestPath) {
        this.network = network;
        this.stations = network.getStations();
        this.stationColor = stationColor;
        this.roadColor = roadColor;
        this.shortestRoadColor = shortestRoadColor;
        this.shortestPath = shortestPath != null ? new ArrayList<>(shortestPath) : new ArrayList<>();

        setPreferredSize(new Dimension(width, height));
        setBackground(bg);
        fillData();
    }

    private void fillData() {
        if (stations.isEmpty()) {
            return;
        }

        boolean hasShortestPath = !shortestPath.isEmpty();
        Station pathStart = hasShortestPath ? shortestPath.get(0).from : null;
        Station pathEnd = hasShortestPath ? shortestPath.get(shortestPath.size() - 1).to : null;

        double theta = 2 * Math.PI / stations.size();
        int index = 0;

        for (Station station : stations.values()) {
            int x = (int) (radius * Math.cos(theta * index) + 0.5 * (width - padding / 2));
            int y = (int) (radius * Math.sin(theta * index) + 0.5 * (height - padding / 2)) + 5;
            boolean highlight = hasShortestPath && (station.equals(pathStart) || station.equals(pathEnd));
            stationComponents.put(station.id, new StationComponent(station, x, y, highlight));
            index++;
        }

        for (Station fromStation : stations.values()) {
            StationComponent fromComp = stationComponents.get(fromStation.id);
            for (Route route : network.getRoutesOfStation(fromStation)) {
                StationComponent toComp = stationComponents.get(route.to.id);
                String key = fromStation.id + "_" + route.to.id;
                routeComponents.put(key, new RouteComponent(fromComp, toComp, route));
                if (shortestPath.contains(route)) {
                    shortestPathComponents.add(routeComponents.get(key));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (RouteComponent routeComponent : routeComponents.values()) {
            g.setColor(shortestPathComponents.contains(routeComponent) ? shortestRoadColor : roadColor);
            routeComponent.drawArrow(g);
        }

        g.setColor(stationColor);
        for (StationComponent stationComponent : stationComponents.values()) {
            stationComponent.drawStation(g);
        }
    }
}
