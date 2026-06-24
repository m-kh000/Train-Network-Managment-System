package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Fileio {

    public static String[] filesNames() {
        // TODO 
        return new String[]{"a","b"};
    }
    
    public static void exportToFile(HashMap  <Station , HashMap<Station , Route>> routes) {
        exportToFile(routes, "data.txt");
    }

    public static void exportToFile(HashMap <Station , HashMap<Station , Route>> routes, String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Station station : routes.keySet()) {
                writer.write(station.id+"-" + station.name + "-> ");
                for (Station station2 : routes.get(station).keySet()) {
                    writer.write( station2.id+ "-" + station2.name + "(" + routes.get(station).get(station2).weight + ") , ");
                    
                }
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importFromFile() {
        importFromFile("data.txt");
    }

    public static void importFromFile(String filePath){
    try(BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
        String line ;
        while ((line = reader.readLine()) != null) {
            String [] parts = line.split("-> ");
            String []source = parts[0].split("-");
            Network.addStation(new Station(source[1], Integer.parseInt(source[0])));
            
        }

    } catch (Exception e) {
        e.printStackTrace();   
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
        String line ;
        while ((line = reader.readLine()) != null) {
            System.out.println(true);
            String [] parts = line.split("-> ");
            String [] r =parts[1].split(" , ");
            String sourceid = parts[0].split("-")[0];
            for (String string : r) {
                  String desid = string.split("-")[0];
                  String wieght = string.split("\\(")[1];
                  wieght= wieght.substring(0, wieght.length()-1);
                  System.out.println(wieght);
                  Network.addRoute(Integer.parseInt(sourceid), Integer.parseInt(desid), Integer.parseInt(wieght));
            }
          
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    }
}
