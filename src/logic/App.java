package logic;

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
        
        //new UI(network);

        System.out.println(network.hasCycle());

    }
}