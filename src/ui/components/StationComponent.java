package ui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import logic.Station;
import ui.Manager;

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

    public void drawStation(Graphics g) {
        Color c = g.getColor();
            g.fillOval(x - radius, y - radius + 10, 2 * radius, 2 * radius - 12);
            g.setColor(Manager.validColor(c.getRed() - 10, c.getGreen() - 45, c.getBlue() - 40));
        if (!big) {
            g.fillOval(x - radius + 6, y - radius + 13, 2 * radius - 12, 2 * radius - 19);
            g.setColor(Manager.validColor(c.getRed() - 30, c.getGreen() - 95, 0));

            drawBGString(g, name, (int) (x + radius + 3), (int) (y - radius - 3));
            g.drawImage(new ImageIcon("public/location1.png").getImage(), x - radius + 1, y - radius - 13, 2 * radius,
                    2 * radius, null);
        } else {
            g.fillOval(x - radius + 5, y - radius + 12, 2 * radius - 10, 2 * radius - 17);
            g.setColor(Manager.validColor(c.getRed() - 30, c.getGreen() - 95, 0));

            drawHIString(g, name, (int) (x + radius + 3), (int) (y - radius - 3));
            g.drawImage(new ImageIcon("public/location1.png").getImage(), x - radius + 1 - 4, y - radius - 13 - 7,
                    2 * radius + 8, 2 * radius + 8, null);
        }
        g.setColor(c);
    }

    private void drawHIString(Graphics g, String name2, int i, int j) {
        g.setFont(Manager.defaultFont(true, false));
        drawBGString(g, name2, i, j);
        g.setFont(Manager.defaultFont(false, false));
    }

    private void drawBGString(Graphics g, String name2, int i, int j) {
        Color prev = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(name, i + 1, j);
        g.drawString(name, i, j + 1);
        g.drawString(name, i - 1, j);
        g.drawString(name, i, j - 1);
        g.drawString(name, i + 2, j);
        g.drawString(name, i, j + 2);
        g.drawString(name, i - 2, j);
        g.drawString(name, i, j - 2);
        g.setColor(prev);
        g.drawString(name, i, j);
    }

}
