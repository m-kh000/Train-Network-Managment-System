package ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import logic.Network;
import logic.Station;
import ui.Manager;

public class ShowStations extends JPanel {
    
    private JPanel stationsPanel;
    private Network network;
    private JToggleButton sortToggle;
    private boolean sortByRoutes = false;
    private Collection<Station> unsorted, sorted;
    
    public ShowStations(Network network) {
        this.network = network;
        unsorted = network.getStations().values();
        sorted = network.getSortedStations();

        setLayout(new BorderLayout());

        // Side panels
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SMALL, Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SMALL));

        // Components
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Manager.topPanel("Privew Stations", network));

        main.add(Box.createVerticalStrut(20));

        // Sort toggle panel
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(Manager.defaultFont(true, false));
        
        sortToggle = new JToggleButton("Route Count");
        sortToggle.setFont(Manager.defaultFont(false, false));
        sortToggle.setPreferredSize(new Dimension(160, 40));
        sortToggle.setSelected(false); // Default to unsorted
        sortToggle.addActionListener(e -> {
            sortByRoutes = sortToggle.isSelected();
            updateStationsDisplay();
        });
        
        togglePanel.add(sortLabel);
        togglePanel.add(sortToggle);
        main.add(togglePanel);
        main.add(Box.createVerticalStrut(15));

        // Stations panel with scroll
        stationsPanel = new JPanel();
        stationsPanel.setLayout(new BoxLayout(stationsPanel, BoxLayout.Y_AXIS));
        stationsPanel.setBorder(BorderFactory.createTitledBorder("Stations List"));
        
        JScrollPane scrollPane = new JScrollPane(stationsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        main.add(scrollPane);
        
        add(main, BorderLayout.CENTER);
        
        // Initial display
        updateStationsDisplay();
    }

    private void updateStationsDisplay() {
        stationsPanel.removeAll();
        
        // Get stations from network - use Network's sorted or unsorted methods
        Collection<Station> stations;
        if (sortByRoutes) {
            stations = sorted;
            sortToggle.setText("Route Count ✓");
        } else {
            stations = unsorted;
            sortToggle.setText("Route Count x");
        }
        
        // Display stations
        if (stations.isEmpty()) {
            JLabel emptyLabel = new JLabel("No stations found in the network.");
            emptyLabel.setFont(Manager.defaultFont(false, true));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            stationsPanel.add(emptyLabel);
        } else {
            for (Station station : stations) {
                stationsPanel.add(createStationPanel(station));
                stationsPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        stationsPanel.revalidate();
        stationsPanel.repaint();
    }

    private JPanel createStationPanel(Station station) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setBackground(Color.WHITE);
        
        // Station info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(station.name);
        nameLabel.setFont(Manager.defaultFont(true, false));
        
        int routeCount = station.getRouts() != null ? station.getRouts().size() : 0;
        JLabel routesLabel = new JLabel("Routes: " + routeCount);
        routesLabel.setFont(Manager.defaultFont(false, false));
        routesLabel.setForeground(Color.DARK_GRAY);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(routesLabel);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Station ID on the right
        JLabel idLabel = new JLabel("ID: " + station.id);
        idLabel.setFont(Manager.defaultFont(false, true));
        idLabel.setForeground(Color.BLUE);
        panel.add(idLabel, BorderLayout.EAST);
        
        return panel;
    }
}