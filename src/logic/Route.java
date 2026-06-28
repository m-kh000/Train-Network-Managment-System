package logic;

public class Route {

    public Station from;
    public Station to;
    public int weight;
    public boolean dou = false;

    public Route(Station from, Station to, int weight , boolean dou) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.dou = dou;
    }

    public boolean isDou() {
        return dou;
    }

    public void setDou(boolean dou){
        this.dou = dou;
    }

}
