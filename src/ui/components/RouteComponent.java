package ui.components;

import java.awt.Graphics;

public class RouteComponent {

    public StationComponent from, to;
    public int weight;
    double ang;
    private double theta = 0.15 * Math.PI;
    private int w = 20;

    public RouteComponent(StationComponent from, StationComponent to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        if (from.x - to.x != 0)
            ang = Math.atan((from.y - to.y) / (from.x - to.x));
        else if (from.y >= to.y) {
            ang = -Math.PI / 2;
        } else {
            ang = Math.PI / 2;
        }
    }

    public void drawArrow(Graphics g) {
        g.drawLine(from.x, from.y, to.x, to.y);
        g.drawLine(to.x, to.y, (int) (to.x - Math.cos(ang - theta) * w), (int) (to.y - Math.sin(ang - theta) * w));
        g.drawLine(to.x, to.y, (int) (to.x - Math.cos(ang + theta) * w), (int) (to.y - Math.sin(ang + theta) * w));
    }
}
