package ui.methods;

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
        
        sortToggle = new JToggleButton(" routes ");
        sortToggle.setFocusPainted(false);
        sortToggle.setFont(Manager.defaultFont(true, false));
        sortToggle.setBackground(Manager.BUTTON_COLOR);
        sortToggle.setForeground(Manager.BUTTON_TEXT);
        sortToggle.setHorizontalAlignment(SwingConstants.CENTER);
        sortToggle.setHorizontalTextPosition(SwingConstants.LEFT);
        sortToggle.setBorder(BorderFactory.createLineBorder(Manager.BUTTON_OUTLINE, 1));
        sortToggle.setOpaque(true);
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
        // scrollPane.setPreferredSize(new Dimension(600, 400));
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
            sortToggle.setText(" routes ");
        } else {
            stations = unsorted;
            sortToggle.setText(" routes ");
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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setBackground(Color.WHITE);
        
        // Station ID on the right
        JLabel idLabel = new JLabel("ID: " + station.id);
        idLabel.setFont(Manager.defaultFont(false, false));
        idLabel.setForeground(Color.BLACK);
        panel.add(idLabel);
        panel.add(Box.createHorizontalStrut(60));
        
        JLabel nameLabel = new JLabel(station.name + "\t");
        nameLabel.setFont(Manager.defaultFont(true, true));
        panel.add(nameLabel);
        panel.add(Box.createHorizontalStrut(530));

        int routeCount = station.getRouts() != null ? station.getRouts().size() : 0;
        JLabel routesLabel = new JLabel("routes: " + routeCount);
        routesLabel.setFont(Manager.defaultFont(false, false));
        routesLabel.setForeground(Color.GRAY);
        panel.add(routesLabel);
        
        return panel;
    }
}