package logic;

public class Station {
    public String name;
    public final int id;
    private static int counter = 1;

    public Station(String name ) {
        this.name = name;
        this.id = counter++;
    }
    public Station (String name , int id){
        this.name = name;
        this.id = id;
        counter ++;
    }

    public String toString() {
        return id+"-"+name;
    }
    // public void modify(String name , HashMap<Integer, Route> routes) {
    //     this.name = name;
    //     this.routes = routes;
    // }
    // public HashMap<Integer,Route> getRouts() {
    //     return routes;
    // }
    // public Route getRoute(int id) {
    //     return routes.get(id);
    // }
}
