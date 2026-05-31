package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class Network {
    private HashMap<Integer, Station> stations = new HashMap<>();

    public void addStation(Station s) {
        stations.put(s.id, s);
    }

    public Station getStation(int id) {
        return stations.get(id);
    }

    public HashMap<Integer, Station> getStations() {
        return stations;
    }
    
    public HashMap<Integer, String> getStations_Name() {
        HashMap<Integer, String> hm = new HashMap<>();
        for(int i : stations.keySet()){
            hm.put(i,stations.get(i).name);
        }
        return hm;
    }

        //ai work did not check it TODO
    public ArrayList<Route> findShortestPath(int fromi, int toi) {
        Station source = stations.get(fromi);
        Station target = stations.get(toi);
        if (source == null || target == null || source == target ) {
            return new ArrayList<>();
        }

        Map<Station, Integer> dist = new HashMap<>();
        Map<Station, Route> previous = new HashMap<>();
        Set<Station> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));

        for (Station station : stations.values()) {
            dist.put(station, Integer.MAX_VALUE);
        }

        dist.put(source, 0);
        queue.add(new Node(source, 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Station current = node.station;

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current == target) {
                break;
            }

            for (Route route : current.getRouts().values()) {
                Station neighbor = route.to;
                int tentative = dist.get(current) + route.weight;
                if (tentative < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, tentative);
                    previous.put(neighbor, route);
                    queue.add(new Node(neighbor, tentative));
                }
            }
        }

        if (!previous.containsKey(target) && source != target) {
            return new ArrayList<>();
        }

        ArrayList<Route> path = new ArrayList<>();
        Station current = target;
        while (current != null && current != source) {
            Route route = previous.get(current);
            if (route == null) {
                break;
            }
            path.add(0, route);
            current = route.from;
        }

        return path;
    }

    private static class Node {
        private Station station;
        private int distance;

        public Node(Station station, int distance) {
            this.station = station;
            this.distance = distance;
        }
    }

    public String[] getStationsID_NameArr() {
        String ans[] = new String[stations.size()];int i = 0;
        for(int s : stations.keySet()){
            ans[i] = "" + s + "-" + stations.get(s).name;
            i++;
        }
        return ans;
    }
}
