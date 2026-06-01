package ui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import logic.Station;

public class StationComponent {

    public int x, y, id;
    public String name;
    public int radius = 18;
    private boolean big = false;

    public StationComponent(Station s, int x, int y, boolean big) {
        this(s, x, y);
        this.big = big;
    }

    public StationComponent(Station s, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = s.name;
        this.id = s.id;
    }

    public String toString() {
        return name + " (" + x + "," + y + ")";
    }

    public void drawStation(Graphics g) {
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        Color c = g.getColor();
        g.setColor(new Color(c.getRed() - 20, c.getGreen() - 80, c.getBlue() - 80));
        if (!big) {
            g.drawString(name, (int) (x + radius), (int) (y - radius));
            g.drawImage(new ImageIcon("public/location1.png").getImage(), x - radius + 1, y - radius - 13, 2 * radius,2 * radius, null);
        } else {
            g.drawString(name, (int) (x + radius + 3), (int) (y - radius - 3));
            g.drawImage(new ImageIcon("public/location1.png").getImage(), x - radius + 1 - 4, y - radius - 13 - 7,2 * radius + 8, 2 * radius + 8, null);
        }
        g.setColor(c);
    }

}
