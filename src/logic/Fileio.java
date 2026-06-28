package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Fileio {
    
    public static void exportToFile() {
        exportToFile("files/recent.txt");
    }

    public static void exportToFile(String filePath) {
        Path targetPath = Path.of(filePath);
        try {
            Path parent = targetPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            for (Station station : Network.routes.keySet()) {
                writer.write(station.id + "-" + station.name + "-> ");
                for (Station station2 : Network.routes.get(station).keySet()) {
                    Route route = Network.routes.get(station).get(station2);
                    writer.write(station2.id + "-" + station2.name + "(" + route.weight + ") , ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importFromFile() {
        importFromFile("files/recent.txt");
    }

    public static void importFromFile(String filePath){
    try(BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
        Network.resetNetwork();
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
            String [] parts = line.split("-> ");
            if (parts.length < 2) continue;
            String [] r = parts[1].split(" , ");
            String sourceid = parts[0].split("-")[0];
            for (String string : r) {
                if (string == null || string.trim().isEmpty() || !string.trim().contains("(") || !string.trim().contains(")"))
                    break;
                String desid = string.split("-")[0].trim();
                String wieght = string.split("\\(")[1];
                wieght = wieght.split("\\)")[0];
                Network.addRoute(Integer.parseInt(sourceid), Integer.parseInt(desid), Integer.parseInt(wieght));
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: sth wen wrong " + e.getMessage());
    }
    }

    public static List<String> TxtFiles() {
        List <String> result = new ArrayList<>();
        File folder = new File("./files");
        File [] files = folder.listFiles();
        if (files != null) {
            for (File file : files) 
                if (file.isFile() && file.getName().endsWith(".txt")) 
                    result.add(file.getName());
        }
        return result;
    }
}
