package ui.methods;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import ui.Manager;
import ui.Manager.Btn;
import ui.components.RouteRow;
import logic.*;

public class EditStation extends JPanel {

    private Network network;
    private JComboBox<String> stationCombo;
    private JTextField nameField = new JTextField();
    private JPanel routesPanel= new JPanel();
    private Btn addRouteBtn = new Btn("");
    private Btn submitBtn;
    private Btn discardBtn;
    private Btn deleteStationBtn;
    private java.util.List<RouteRow> routeRows = new java.util.ArrayList<>();
    private Station currentStation;
    
    public EditStation(Network network) {
        this.network = network;
        
        setLayout(new BorderLayout());
        setBackground(Manager.defaultBGColor());

        // Side panels
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE));

        // Components
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Manager.topPanel("Edit Station", network));

        // Select station panel
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        JLabel selectLabel = new JLabel("Select Station:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        stationCombo = new JComboBox<>(network.getStationsID_Name());
        stationCombo.setSelectedItem(null);
        stationCombo.setFont(Manager.defaultFont(false, false));
        
        selectPanel.add(selectLabel);
        selectPanel.add(stationCombo);
        main.add(selectPanel);
        main.add(Box.createVerticalStrut(15));

        // --- Station name row
        JPanel nameRow = new JPanel(new BorderLayout(10, 0));
        JLabel nameLabel = new JLabel("Station Name:");
        nameLabel.setFont(Manager.defaultFont(true, false));
        nameLabel.setPreferredSize(new Dimension(150, 30));
        nameField.setFont(Manager.defaultFont(false, false));
        nameRow.add(nameLabel, BorderLayout.WEST);
        nameRow.add(nameField, BorderLayout.CENTER);
        main.add(nameRow);
        main.add(Box.createVerticalStrut(20));

        // Routes management panel
        JPanel routesHeaderPanel = new JPanel(new BorderLayout());
        JLabel routesLabel = new JLabel("Routes:");
        routesLabel.setFont(Manager.defaultFont(true, false));
        
        Btn addRouteBtn = new Btn(Manager.ADD_PATH, "Add Route");
        addRouteBtn.setPreferredSize(new Dimension(140, 30));
        addRouteBtn.addActionListener(e -> addRouteRow());
        
        routesHeaderPanel.add(routesLabel, BorderLayout.WEST);
        routesHeaderPanel.add(addRouteBtn, BorderLayout.EAST);
        main.add(routesHeaderPanel);
        main.add(Box.createVerticalStrut(10));

        // --- Routes list (scrollable)
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Routes"));
        JScrollPane scroll = new JScrollPane(routesPanel);
        scroll.setPreferredSize(new Dimension(500, 400));
        main.add(scroll);

        //delete station button
        JPanel deleteRow = new JPanel(new BorderLayout());
        deleteRow.setPreferredSize(new Dimension(40,40));
        deleteRow.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        deleteStationBtn = new Btn("", "Delete Station");
        deleteRow.add(deleteStationBtn,BorderLayout.CENTER);
        deleteStationBtn.setBackground(new Color(220, 60, 60));
        main.add(deleteRow);
        main.add(Box.createVerticalStrut(10));

        // Action buttons panel
        JPanel actionPanel = new JPanel(new GridLayout(1,2,20,20));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        submitBtn = new Btn("", "Save");
        discardBtn = new Btn("", "Discard");

        actionPanel.add(submitBtn);
        actionPanel.add(discardBtn);
        main.add(actionPanel);

        add(main, BorderLayout.CENTER);

        // Initially disable form (no station selected)
        setFormEnabled(false);
        
        // ==================== Listeners ====================

        // Auto-populate fields when station is selected
        stationCombo.addActionListener(e -> {
            Station selected = (Station) stationCombo.getSelectedItem();
            if (selected != null) {
                currentStation = selected;
                loadStationData(selected);
                // Enable all fields and buttons
                setFormEnabled(true);
            } else {
                // No station selected, disable form
                currentStation = null;
                clearForm();
                setFormEnabled(false);
            }
        });

        // Enter key functionality
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "save");
        getActionMap().put("save", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                submitBtn.doClick();
            }
        });


        // Submit button - save all changes
        submitBtn.addActionListener(e -> {
            if (currentStation == null) {
                JOptionPane.showMessageDialog(null, "Please select a station first.");
                return;
            }
            
            try {
                // Validate station name field
                String newName = nameField.getText().trim();
                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Station name cannot be empty.");
                    return;
                }
                
                // Validate all route rows
                HashMap<Integer, Route> updatedRoutes = new HashMap<>();
                
                for (RouteRow row : routeRows) {
                    if (row.toSelected() == null) {
                        JOptionPane.showMessageDialog(null, "Please select a destination station for all routes.");
                        return;
                    }
                    
                    String to = (String) row.toSelected();
                    String weightText = row.getWeight();
                    
                    if (weightText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a weight for all routes.");
                        return;
                    }
                    
                    // Validate weight is a number
                    int weight;
                    try {
                        weight = Integer.parseInt(weightText);
                        if (weight <= 0) {
                            JOptionPane.showMessageDialog(null, "Route weight must be a positive number.");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Route weight must be a valid number.");
                        return;
                    }
                    
                    int toId = Integer.parseInt(to.split("-")[0]);
                    // Check: station cannot have a route to itself
                    if (toId == currentStation.id) {
                        JOptionPane.showMessageDialog(null, "A station cannot have a route to itself.");
                        return;
                    }
                    
                    // Create route and add to map
                    Route route = new Route(currentStation, network.findStationById(to), weight);
                    updatedRoutes.put(toId, route);
                }
                
                // Call modifyStation method with validated data
                network.modifyStation(currentStation.id, newName, updatedRoutes);
                JOptionPane.showMessageDialog(null, "Station updated successfully!");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Discard button - reset to original values
        discardBtn.addActionListener(e -> {
            if (currentStation != null) {
                loadStationData(currentStation);
                JOptionPane.showMessageDialog(null, "Changes discarded.");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a station first.");
            }
        });

        // Delete station button
        deleteStationBtn.addActionListener(e -> {
            if (currentStation == null) {
                JOptionPane.showMessageDialog(null, "Please select a station first.");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Delete station '" + currentStation.name + "'?\nThis will also remove all routes to/from this station.", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                network.deleteStation(currentStation);
                JOptionPane.showMessageDialog(null, "Station deleted successfully.");
                stationCombo.removeItem(currentStation);
                clearForm();
            }
        });
    }

    private void loadStationData(Station station) {
        nameField.setText(station.name);
        
        // Load existing routes
        routesPanel.removeAll();
        routeRows.clear();
        
        for (Route route : station.getRouts().values()) {
            RouteRow row = new RouteRow(network,routesPanel,routeRows,route.to.toString(), route.weight);
            routeRows.add(row);
            routesPanel.add(row);
        }
        
        routesPanel.revalidate();
        routesPanel.repaint();
    }

    private void addRouteRow() {
        RouteRow row = new RouteRow(network,routesPanel,routeRows);
        routeRows.add(row);
        routesPanel.add(row);
        routesPanel.revalidate();
        routesPanel.repaint();
    }

    private void clearForm() {
        nameField.setText("");
        routesPanel.removeAll();
        routeRows.clear();
        routesPanel.revalidate();
        routesPanel.repaint();
        currentStation = null;
        stationCombo.setSelectedItem(null);
        setFormEnabled(false);
    }

    // Enable/disable form fields based on station selection
    private void setFormEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        addRouteBtn.setEnabled(enabled);
        submitBtn.setEnabled(enabled);
        discardBtn.setEnabled(enabled);
        deleteStationBtn.setEnabled(enabled);
        
        // Enable/disable all existing route rows
        for (RouteRow row : routeRows) {
            row.setEnabled(false);
        }
    }

}