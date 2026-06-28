package ui.components;

import java.awt.Color;
import java.awt.Graphics;

import logic.Route;
import ui.Manager;

public class RouteComponent {

    public StationComponent from, to;
    public int weight;
    private double ang;
    private double theta = 0.1 * Math.PI;
    private int w = 30;
    private double stepback;
    private int bx, by, shv = 5, shbx, shby, shx, shy, fx, fy;
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

        if (dou && (from.x < to.x || (from.x == to.x && from.y < to.y))) {
            shv = -shv;
        }
        stepback = from.radius;
        bx = (int) (to.x - Math.cos(ang) * stepback);
        by = (int) (to.y - Math.sin(ang) * stepback);
        fx = (int) (from.x + Math.cos(ang) * stepback);
        fy = (int) (from.y + Math.sin(ang) * stepback);
    }

    public void drawArrow(Graphics g) {
        shbx = bx + shv;
        shby = by + shv;
        shx = fx + shv;
        shy = fy + shv;

        drawThickLine(g,shx, shy, shbx, shby);
        drawThickLine(g,shbx, shby, (int) (shbx - Math.cos(ang - theta) * w), (int) (shby - Math.sin(ang - theta) * w));
        drawThickLine(g,shbx, shby, (int) (shbx - Math.cos(ang + theta) * w), (int) (shby - Math.sin(ang + theta) * w));
        int my , mx ;
        if (shv > 0){
            my = shby + (shy - shby) / 4 * 3 + shv * 2 / 3;
            mx = shbx + (shx - shbx) / 4 * 3 + shv ;
        }
        else{
            my = (int) (shby + (shy - shby) / 4 * 3 + shv * 0.8);
            mx = shbx + (shx - shbx) / 4 * 3 + shv * 4;
        }
        Color c = g.getColor();
        g.setColor(Manager.validColor(c.getRed() - 30, c.getGreen() - 90, c.getBlue() - 90));
        g.drawString(String.valueOf(weight), mx, my);
        g.setColor(c);
    }

    private void drawThickLine(Graphics g, int shx2, int shy2, int shbx2, int shby2) {
        g.drawLine(shx2, shy2, shbx2, shby2);
        
        
        g.drawLine(shx2+1, shy2, shbx2+1, shby2);
        // g.drawLine(shx2-1, shy2, shbx2-1, shby2);
        g.drawLine(shx2, shy2+1, shbx2, shby2+1);
        g.drawLine(shx2, shy2-1, shbx2, shby2-1);
    }
}
