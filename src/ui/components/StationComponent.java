package ui.components;

import java.awt.Graphics;

import logic.Station;

public class StationComponent {

    public int x, y,id;
    public String name;
    public int radius = 20 ;
    public StationComponent(Station s, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = s.name;
        this.id = s.id;
    }
    public String toString(){
        return name+" ("+x+","+y+")";
    }
    public void drawStation(Graphics g) {
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString(name, x , (int)(y - 1.5 * radius));
    }

}
