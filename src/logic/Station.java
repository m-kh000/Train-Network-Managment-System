package logic;

import java.util.HashMap;

public class Station {
    public String name;
    public final int id;
    private static int counter = 1;
    private HashMap<Integer, Route> routes;

    public Station(String name , HashMap<Integer, Route> routes) {
        this.name = name;
        this.id = counter++;
        this.routes = routes;
    }
    public Station(String name) {
        this(name, new HashMap<>());
    }

    public void addRoute(Route r) {
        routes.put(r.to.id, r);
    }

    public String toString() {
        return id+"-"+name;
    }
    public void modify(String name , HashMap<Integer, Route> routes) {
        this.name = name;
        this.routes = routes;
    }
    public HashMap<Integer,Route> getRouts() {
        return routes;
    }
    public Route getRoute(int id) {
        return routes.get(id);
    }
}
