package logic;

import java.util.HashMap;

public class Station {
    public final String name;
    public final int id;
    private static int counter = 1;
    private HashMap<Integer, Route> routes = new HashMap<>();

    public Station(String name) {
        this.name = name;
        this.id = counter++;
    }

    public void addRoute(Route r) {
        routes.put(r.to.id, r);
    }

    public Route getRoute(int toId) {
        return routes.get(toId);
    }

    public HashMap<Integer, Route> getRouts() {
        return routes;
    }
}
