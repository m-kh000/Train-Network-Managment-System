package logic;

import javax.swing.JFrame;

import ui.GraphPage;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        HashMap<Integer, Station> data = new HashMap<>();
        
        // Add stations
        data.put(1, new Station("A"));
        data.put(2, new Station("B"));
        data.put(3, new Station("C"));
        data.put(4, new Station("D"));
        data.put(5, new Station("E"));
        data.put(6, new Station("F"));
        data.put(7, new Station("G"));

        // Add routes
        data.get(1).addRoute(new Route(data.get(1), data.get(2), 10));
        data.get(1).addRoute(new Route(data.get(1), data.get(3), 20));
        data.get(1).addRoute(new Route(data.get(1), data.get(5), 15));
        data.get(2).addRoute(new Route(data.get(2), data.get(4), 30));
        data.get(2).addRoute(new Route(data.get(2), data.get(6), 25));
        data.get(3).addRoute(new Route(data.get(3), data.get(4), 12));
        data.get(3).addRoute(new Route(data.get(3), data.get(7), 35));
        data.get(5).addRoute(new Route(data.get(5), data.get(6), 18));
        data.get(6).addRoute(new Route(data.get(6), data.get(7), 28));
        

        JFrame frame = new JFrame();
        frame.add(new GraphPage(data));
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
