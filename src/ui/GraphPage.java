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

    public GraphPage(HashMap<Integer, Station> stations) {
        this.stations = stations;
        fillData();
    }

    private void fillData() {

        //jfn
        int count = 0;

        for (Station s : stations.values()) {
            st_ui.put(s.id, new StationComponent(s, (int)(100 + count * 100 * Math.random())%1000, (int)(50 + count *320* Math.random())%800));
            count++;
        }
        for (Station from : stations.values()) {
            StationComponent fromComp = st_ui.get(from.id);
            for (Route r : from.getRouts().values()) {
                StationComponent toComp = st_ui.get(r.to.id);
                ro_ui.put(from.id + "_" + r.to.id, new RouteComponent(fromComp, toComp, r.weight));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (RouteComponent r : ro_ui.values()) {
            g.setColor(shortestPath.contains(r) ? Color.RED : Color.BLACK);
            r.drawArrow(g);
            int mx = (r.from.x + r.to.x) / 2;
            int my = (r.from.y + r.to.y) / 2;
            g.drawString(String.valueOf(r.weight), mx, my);
        }

        g.setColor(Color.BLUE);
        for (StationComponent s : st_ui.values()) {
            g.fillOval(s.x - 10, s.y - 10, 20, 20);
            g.drawString(s.name, s.x + 10, s.y);
        }
    }
}
