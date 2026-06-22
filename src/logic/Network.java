package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Network {
    private HashMap<Integer, Station> stations = new HashMap<>();
    private Map <Station , List<Route>> routes = new HashMap<>();

    public Network(File file) {
        // TODO Auto-generated constructor stub
    }

    public Network() {
        // TODO Auto-generated constructor stub
    }

    public void addStation(Station s) {
        stations.put(s.id, s);
        routes.put(s, new ArrayList<>());
    }

    public Station getStation(int id) {
        return stations.get(id);
    }

    public HashMap<Integer, Station> getStations() {
        return stations;
    }

    public void addRoute(int from, int to , int weight){
        Station fromStation = stations.get(from);
        if (fromStation == null) 
            throw new IllegalArgumentException("Station with ID " + from + " not found");
        Station toStation = stations.get(to);
        if (toStation == null) 
            throw new IllegalArgumentException("Station with ID " + to + " not found");
        
        routes.get(fromStation).add(new Route(fromStation, toStation, weight));
    }


    public HashMap<Integer, String> getStations_Name() {
        HashMap<Integer, String> hm = new HashMap<>();
        for (int i : stations.keySet()) {
            hm.put(i, stations.get(i).name);
        }
        return hm;
    }

    // ai work did not check it TODO
        public ArrayList<Route> findShortestPath(int fromi, int toi) {return null;}
    //     Station source = stations.get(fromi);
    //     Station target = stations.get(toi);
    //     if (source == null || target == null || source == target) {
    //         return new ArrayList<>();
    //     }

    //     Map<Station, Integer> dist = new HashMap<>();
    //     Map<Station, Route> previous = new HashMap<>();
    //     Set<Station> visited = new HashSet<>();
    //     PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));

    //     for (Station station : stations.values()) {
    //         dist.put(station, Integer.MAX_VALUE);
    //     }

    //     dist.put(source, 0);
    //     queue.add(new Node(source, 0));

    //     while (!queue.isEmpty()) {
    //         Node node = queue.poll();
    //         Station current = node.station;

    //         if (visited.contains(current)) {
    //             continue;
    //         }
    //         visited.add(current);

    //         if (current == target) {
    //             break;
    //         }

    //         for (Route route : current.getRouts().values()) {
    //             Station neighbor = route.to;
    //             int tentative = dist.get(current) + route.weight;
    //             if (tentative < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
    //                 dist.put(neighbor, tentative);
    //                 previous.put(neighbor, route);
    //                 queue.add(new Node(neighbor, tentative));
    //             }
    //         }
    //     }

    //     if (!previous.containsKey(target) && source != target) {
    //         return new ArrayList<>();
    //     }

    //     ArrayList<Route> path = new ArrayList<>();
    //     Station current = target;
    //     while (current != null && current != source) {
    //         Route route = previous.get(current);
    //         if (route == null) {
    //             break;
    //         }
    //         path.add(0, route);
    //         current = route.from;
    //     }

    //     return path;
    // }

   
    public String[] getStationsID_Name() {
        String ans[] = new String[stations.size()];
        int i = 0;
        for (Station s : stations.values()) {
            ans[i] = s.toString();
            i++;
        }
        return ans;
    }

    // Modify station with new name and routes
    public void modifyStation(int stationId, String name, HashMap<Integer, Route> routes) {
        Station station = stations.get(stationId);
        if (station == null) {
            throw new IllegalArgumentException("Station with ID " + stationId + " not found");
        }
        
        // Update station name
        station.modify(name , routes);
    }

    public Station findStationById(String to) {
        // Extract ID from string like "1-StationName"
        int id = Integer.parseInt(to.split("-")[0]);
        return stations.get(id);
    }

    public void deleteStation(Station station) {
        stations.remove(station.id);
        
        // Also remove all routes pointing to this station from other stations
        for (Station s : stations.values()) {
            s.getRouts().remove(station.id);
        }
    }

    // Get stations sorted by number of routes (descending)
    public Collection<Station> getSortedStations() {
        ArrayList<Station> sortedStations = new ArrayList<>(stations.values());
        
        sortedStations.sort((s1, s2) -> {
            int routes1 = s1.getRouts() != null ? s1.getRouts().size() : 0;
            int routes2 = s2.getRouts() != null ? s2.getRouts().size() : 0;
            return Integer.compare(routes2, routes1); // Descending order
        });
        
        return sortedStations;
    }
    
    // Get stations in natural/unsorted order (as stored in HashMap)
    public Collection<Station> getUnsortedStations() {
        return stations.values();
    }

    public boolean hasCycle (){
        Set <Station> all = new HashSet<>();
        all.addAll(stations.values());
        Set <Station> visiting = new HashSet<>();
        Set <Station> visited = new HashSet<>();

        while(!all.isEmpty()){
            Station current = all.iterator().next();
            if (hasCycle(current, all,   visiting, visited)) 
                return true;
        }
        return false;
    }
    
    // recursive function  
    private boolean hasCycle(Station station , Set <Station> all , Set <Station> visiting , Set<Station> visited){
        all.remove(station);
        visiting.add(station);

        for (Route route : routes.get(station)) {
            if (visited.contains(route.to))
                continue;
            if (visiting.contains(route.to)) 
                return true;
            if (hasCycle(route.to, all, visiting, visited)) 
                return true;
        }
        visiting.remove(station);
        visited.add(station);
        return false;
    }
}
