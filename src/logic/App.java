package logic;

import java.util.HashMap;

import ui.UI;

public class App {

    public static void main(String[] args) throws Exception {
        Network network = new Network();
        
        network.addStation(new Station("A"));
        network.addStation(new Station("B"));
        network.addStation(new Station("C"));
        network.addStation(new Station("D"));
        // network.addStation(new Station("E"));
        // network.addStation(new Station("F"));
        // network.addStation(new Station("G"));
        // network.addStation(new Station("G"));
        // network.addStation(new Station("G"));
        // System.out.println(network.getStation(1));

        for (int s : network.stations.keySet()) {
            System.out.println(s  + "   "  + network.stations.get(s) );
        }

        network.addRoute(1,2, 10);
        network.addRoute(2, 3, 100);
        network.addRoute(3, 1, 50);
        network.addRoute(4, 2, 50);
        network.addRoute(4, 3, 50);
        // network.getStation(1).addRoute(new Route(network.getStation(1), network.getStation(3), 20));
        // network.getStation(1).addRoute(new Route(network.getStation(1), network.getStation(5), 15));
        // network.getStation(2).addRoute(new Route(network.getStation(2), network.getStation(4), 30));
        // network.getStation(2).addRoute(new Route(network.getStation(2), network.getStation(6), 25));
        // network.getStation(3).addRoute(new Route(network.getStation(3), network.getStation(4), 12));
        // network.getStation(3).addRoute(new Route(network.getStation(3), network.getStation(7), 35));
        // network.getStation(7).addRoute(new Route(network.getStation(7), network.getStation(3), 1035));
        // network.getStation(5).addRoute(new Route(network.getStation(5), network.getStation(6), 18));
        // network.getStation(6).addRoute(new Route(network.getStation(6), network.getStation(5), 28));

        new UI(network);

        System.out.println(network.hasCycle());
        network.deleteStation(network.stations.get(1));
        for (int s : network.stations.keySet()) {
            System.out.println(s  + "   "  + network.stations.get(s) );
        }
        for (Station station : network.routes.keySet()) {
            System.out.println( station.name  + "    " + network.routes.get(station).size());
        }

        for (Station station : network.getSortedStations()) {
            System.out.println(station);
        }
    }
}