package logic;

import ui.UI;

public class App {
    private static Network network;

    public static void main(String[] args) throws Exception {
        Network network = new Network();
        
        network.addStation(new Station("A"));
        network.addStation(new Station("B"));
        network.addStation(new Station("C"));
        network.addStation(new Station("D"));
        network.addStation(new Station("E"));
        network.addStation(new Station("F"));
        network.addStation(new Station("G"));

        network.getStation(1).addRoute(new Route(network.getStation(1), network.getStation(2), 10));
        network.getStation(1).addRoute(new Route(network.getStation(1), network.getStation(3), 20));
        network.getStation(1).addRoute(new Route(network.getStation(1), network.getStation(5), 15));
        network.getStation(2).addRoute(new Route(network.getStation(2), network.getStation(4), 30));
        network.getStation(2).addRoute(new Route(network.getStation(2), network.getStation(6), 25));
        network.getStation(3).addRoute(new Route(network.getStation(3), network.getStation(4), 12));
        network.getStation(3).addRoute(new Route(network.getStation(3), network.getStation(7), 35));
        network.getStation(7).addRoute(new Route(network.getStation(7), network.getStation(3), 1035));
        network.getStation(5).addRoute(new Route(network.getStation(5), network.getStation(6), 18));
        network.getStation(6).addRoute(new Route(network.getStation(6), network.getStation(5), 28));

        new UI(network);
    }
}