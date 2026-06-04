package logic;

import java.util.HashMap;

public class Station {
    public String name;
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

    // Remove a route to a specific station
    public void removeRoute(int toId) {
        routes.remove(toId);
    }

    // Remove all routes (for station deletion)
    public void clearRoutes() {
        routes.clear();
    }

    // Update station name
    public void setName(String newName) {
        this.name = newName;
    }

    public String toString() {
        return id+"_"+name;
    }
}
