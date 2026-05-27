package ui.components;

import logic.Station;

public class StationComponent {

    public int x, y,id;
    public String name;
    public StationComponent(Station s, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = s.name;
        this.id = s.id;
    }
}
