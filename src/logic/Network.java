package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Network {
    public static HashMap<Integer, Station> stations = new HashMap<>();
    public static HashMap <Station , HashMap<Station , Route>> routes = new HashMap<>();

    public Network(String filepath) {
        Fileio.importFromFile(filepath);
    }

    public Network() {
        // TODO Auto-generated constructor stub
    }

    public static void addStation(Station s) {
        stations.put(s.id, s);
        routes.putIfAbsent(s, new HashMap<>());
    }

    public Station getStation(int id) {
        return stations.get(id);
    }

    public HashMap<Integer, Station> getStations() {
        return stations;
    }

    public static void addRoute(int from, int to , int weight){
        Station fromStation = stations.get(from);
        if (fromStation == null) 
            throw new IllegalArgumentException("Station with ID " + from + " not found");
        Station toStation = stations.get(to);
        if (toStation == null) 
            throw new IllegalArgumentException("Station with ID " + to + " not found");
        
        boolean dou = false ;

        if (routes.get(toStation).get(fromStation)== null) {
            dou = false;
        }
        else 
            dou = true;
        routes.get(fromStation).putIfAbsent(toStation,new Route(fromStation, toStation, weight , dou));
        

    }

    // TODO
        public int findShortestPath(int from, int to, ArrayList<Route> finalpath) {return 0;}
   
    public String[] getStationsID_Name() {
        String ans[] = new String[stations.size()];
        try{
        int i = 0;
        for (Station s : stations.values()) {
            ans[i] = s.toString();
            i++;
        }}catch(Exception e){System.out.println("found itttttt");}
        return ans;
    }

    public Station findStationById_Name(String to) {
        // Extract ID from string like "1-StationName"
        int id = Integer.parseInt(to.split("-")[0]);
        return stations.get(id);
    }

     public void deleteStation(Station station) {
         stations.remove(station.id);
         routes.remove(station);
        
         // Also remove all routes pointing to this station from other stations
         for (Station s : routes.keySet()) {
                if (routes.get(s).containsKey(station)) {
                    routes.get(s).remove(station);
                }
         }
     }

    // Get stations sorted by number of routes (descending)
     public Collection<Station> getSortedStations() {
         ArrayList<Station> sortedStations = new ArrayList<>(stations.values());
        
         sortedStations.sort((s1, s2) -> {
            int routes1 = routes.get(s1).size();
            int routes2 = routes.get(s2).size();
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

        for (Route route : routes.get(station).values()) {
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

    public List<Route> getRoutesOfStation(Station station) {
        List <Route> result = new ArrayList<>();
        for (Route route : routes.get(station).values()) {
            result.add(route);
        }

        return result;
    }

    public void deleteallRoutesOfStation(Station station) {
        routes.put(station, new HashMap<Station,Route>());
    }

}
