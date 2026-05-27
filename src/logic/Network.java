package logic;

import java.util.HashMap;

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
}
