package logic;

public class Station {
    public String name;
    public final int id;
    private static int counter = 1;
    public static int max = 0;

    public Station(String name ) {
        this.name = name;
        this.id = counter++;
    }
    public Station (String name , int id){
        this.name = name;
        this.id = id;
        if (max < id) max = id;
        counter  = max + 1 ;
    }

    public String toString() {
        return id+"-  "+name;
    }
    public void setName(String newName) {
        name = newName;
    }
    public static void reset() {
        counter = 0;
        max = 0;
    }
}
