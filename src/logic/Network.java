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

    public static void addStation(Station s) {
        stations.put(s.id, s);
        routes.putIfAbsent(s, new HashMap<>());
    }

    public static Station getStation(int id) {
        return stations.get(id);
    }

    public static HashMap<Integer, Station> getStations() {
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


        public static int findShortestPath(int from, int to, ArrayList<Route> finalpath) {
            finalpath.clear();
            Station[] stationssorted = stations.values().toArray(new Station[0]);
            int size = stationssorted.length;
            int[] dis = new int[size];
            int[] prev = new int[size];
            boolean[] visited = new boolean[size];
            java.util.HashMap<Integer, Integer> indexById = new java.util.HashMap<>();

            for (int i = 0; i < size; i++) {
                indexById.put(stationssorted[i].id, i);
                dis[i] = Integer.MAX_VALUE;
                prev[i] = -1;
            }

            int startIndex = indexById.getOrDefault(from, -1);
            int targetIndex = indexById.getOrDefault(to, -1);
            if (startIndex == -1 || targetIndex == -1) {
                return 0;
            }

            dis[startIndex] = 0;

            for (int i = 0; i < size; i++) {
                int smallest = -1;
                for (int j = 0; j < size; j++) {
                    if (!visited[j] && (smallest == -1 || dis[j] < dis[smallest])) {
                        smallest = j;
                    }
                }

                if (smallest == -1 || dis[smallest] == Integer.MAX_VALUE) {
                    break;
                }
                visited[smallest] = true;

                if (smallest == targetIndex) {
                    break;
                }

                Station current = stationssorted[smallest];
                HashMap<Station, Route> neighbors = routes.get(current);

                for (Route r : neighbors.values()) {
                    Integer neighborIndex = indexById.get(r.to.id);
                    if (neighborIndex == null) {
                        continue;
                    }
                    int newDist = dis[smallest] + r.weight;
                    if (newDist < dis[neighborIndex]) {
                        dis[neighborIndex] = newDist;
                        prev[neighborIndex] = smallest;
                    }
                }
            }

            if (dis[targetIndex] == Integer.MAX_VALUE) {
                return 0;
            }

            int currentIndex = targetIndex;
            while (currentIndex != startIndex) {
                int previousIndex = prev[currentIndex];
                if (previousIndex == -1) {
                    finalpath.clear();
                    return 0;
                }
                Station fromStation = stationssorted[previousIndex];
                Station toStation = stationssorted[currentIndex];
                Route route = routes.get(fromStation).get(toStation);
                if (route == null) {
                    finalpath.clear();
                    return 0;
                }
                finalpath.add(route);
                currentIndex = previousIndex;
            }

            java.util.Collections.reverse(finalpath);
            return dis[targetIndex];
        }
   
    public static String[] getStationsID_Name() {
        String ans[] = new String[stations.size()];
        try{
        int i = 0;
        for (Station s : stations.values()) {
            ans[i] = s.toString();
            i++;
        }}catch(Exception e){System.out.println("found itttttt");}
        return ans;
    }

    public static Station findStationById_Name(String to) {
        // Extract ID from string like "1-StationName"
        int id = Integer.parseInt(to.split("-")[0]);
        return stations.get(id);
    }

     public static void deleteStation(Station station) {
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
     public static Collection<Station> getSortedStations() {
         ArrayList<Station> sortedStations = new ArrayList<>(stations.values());
        
         sortedStations.sort((s1, s2) -> {
            int routes1 = routes.get(s1).size();
            int routes2 = routes.get(s2).size();
            return Integer.compare(routes2, routes1); // Descending order
        });
        
        return sortedStations;
    }
    
    // Get stations in natural/unsorted order (as stored in HashMap)
    public static Collection<Station> getUnsortedStations() {
        return stations.values();
    }

    public static boolean hasCycle (){
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
    private static boolean hasCycle(Station station , Set <Station> all , Set <Station> visiting , Set<Station> visited){
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

    public static List<Route> getRoutesOfStation(Station station) {
        List <Route> result = new ArrayList<>();
        for (Route route : routes.get(station).values()) {
            result.add(route);
        }

        return result;
    }

    public static void deleteallRoutesOfStation(Station station) {
        routes.put(station, new HashMap<Station,Route>());
    }

    public static void resetNetwork() {
        Station.reset();
        stations.clear();
        routes.clear();
    }

}
