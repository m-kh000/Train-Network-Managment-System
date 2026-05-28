package ui.components;

import java.awt.Graphics;

import logic.Route;

public class RouteComponent {

    public StationComponent from, to;
    public int weight;
    private double ang;
    private double theta = 0.1 * Math.PI;
    private int w = 30;
    private double stepback;
    private int bx, by, shv = 5, shbx, shby, shx, shy;
    private boolean dou = false;

    public RouteComponent(StationComponent from, StationComponent to, Route r) {
        this.from = from;
        this.to = to;
        this.weight = r.weight;
        this.dou = r.dou;
        if (from.x - to.x != 0) {
            ang = Math.atan(1.0 * (from.y - to.y) / (from.x - to.x));
        } else if (from.y >= to.y) {
            ang = -Math.PI / 2;
        } else {
            ang = Math.PI / 2;
        }

        if (from.x > to.x) {
            ang += Math.PI;
        }

        if (dou && from.x < to.x) {
            shv = -shv;
        }
        stepback = from.radius;
        bx = (int) (to.x - Math.cos(ang) * stepback);
        by = (int) (to.y - Math.sin(ang) * stepback);
    }

    public void drawArrow(Graphics g) {
        shbx = bx + shv;
        shby = by + shv;
        shx = from.x + shv;
        shy = from.y + shv;

        System.out.println("i drew the line from " + from + " to " + to);
        g.drawLine(shx, shy, shbx, shby);
        g.drawLine(shbx, shby, (int) (shbx - Math.cos(ang - theta) * w), (int) (shby - Math.sin(ang - theta) * w));
        g.drawLine(shbx, shby, (int) (shbx - Math.cos(ang + theta) * w), (int) (shby - Math.sin(ang + theta) * w));
        int mx = shbx + (shx - shbx) / 4 * 3 + shv;
        int my;
        if (shv > 0)
            my = shby + (shy - shby) / 4 * 3 + shv ;
        else
            my = (int) (shby + (shy - shby) / 4 * 3 + shv * 0.5);
        g.drawString(String.valueOf(weight), mx, my);
    }
}
