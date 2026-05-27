package logic;

public class Route {

    public Station from;
    public Station to;
    public int weight;
    public boolean dou = false;

    public Route(Station from, Station to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        if (isDou()) {
            dou = true;
            findDou().dou = true;
        }
    }

    private boolean isDou() {
        for (Route r : to.getRouts().values()) {
            if (r.to == from)
                return true;
        }
        return false;
    }

    private Route findDou() {
        return to.getRoute(from.id);
    }

}
