package logic;
public class Route {

    public Station from;
    public Station to;
    public int weight;

    public Route(Station from, Station to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
}
