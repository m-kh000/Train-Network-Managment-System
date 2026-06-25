package ui.methods;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import ui.Manager;
import ui.Manager.Btn;
import ui.components.RouteRow;
import logic.*;

public class EditStation extends JPanel {

    private final JComboBox<String> stationCombo = new JComboBox<>();
    private final JTextField nameField = new JTextField();
    private final JPanel routesPanel = new JPanel();
    private final Btn addRouteBtn;
    private final Btn submitBtn;
    private final Btn discardBtn;
    private final Btn deleteStationBtn;
    private final java.util.List<RouteRow> routeRows = new java.util.ArrayList<>();
    private boolean isUpdatingForm = false;
    private Station currentStation = null;
    
    public EditStation() {
        
        setLayout(new BorderLayout());

        // Side panels
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE));

        // Components
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Manager.topPanel("Edit Station"));

        // Select station panel
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BorderLayout());
        JLabel selectLabel = new JLabel("Select Station:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        selectLabel.setPreferredSize(new Dimension(150, 30));
        stationCombo.setFont(Manager.defaultFont(false, false));
        fillStationComboBox();
        
        selectPanel.add(selectLabel, BorderLayout.WEST);
        selectPanel.add(stationCombo, BorderLayout.CENTER);
        main.add(selectPanel);
        main.add(Box.createVerticalStrut(15));

        // --- Station name row
        JPanel nameRow = new JPanel(new BorderLayout());
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
        
        addRouteBtn = new Btn(Manager.ADD_PATH, "Add Route",false);
        addRouteBtn.setPreferredSize(new Dimension(160, 30));
        addRouteBtn.addActionListener(e -> addRouteRow());
        
        routesHeaderPanel.add(routesLabel, BorderLayout.WEST);
        routesHeaderPanel.add(addRouteBtn, BorderLayout.EAST);
        main.add(routesHeaderPanel);
        main.add(Box.createVerticalStrut(10));

        // --- Routes list (scrollable)
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Routes"));
        JScrollPane scroll = new JScrollPane(routesPanel);
        scroll.setPreferredSize(new Dimension(500, 350));
        main.add(scroll);

        //delete station button
        JPanel deleteRow = new JPanel(new BorderLayout());
        deleteRow.setPreferredSize(new Dimension(50,50));
        deleteRow.setBorder(BorderFactory.createEmptyBorder(10, 200, 0, 200));
        deleteStationBtn = new Btn("", "Delete Station", false);
        deleteRow.add(deleteStationBtn,BorderLayout.CENTER);
        
        deleteStationBtn.setBackground(Manager.defaultBGColor());
        deleteStationBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 60, 60)));
        deleteStationBtn.setForeground(new Color(180, 60, 60));
        main.add(deleteRow);
        main.add(Box.createVerticalStrut(10));

        // Action buttons panel
        JPanel actionPanel = new JPanel(new GridLayout(1,2,20,20));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        actionPanel.setPreferredSize(new Dimension(55,55));
        submitBtn = new Btn("", "Save");
        discardBtn = new Btn("", "Discard");

        actionPanel.add(submitBtn);
        actionPanel.add(discardBtn);
        main.add(actionPanel);

        add(main, BorderLayout.CENTER);

        clearForm();
        setFormEnabled(false);
        
        // ==================== Listeners ====================

        // Auto-populate fields when station is selected
        stationCombo.addActionListener(e -> {
            if (isUpdatingForm) return;
            String name = (String) stationCombo.getSelectedItem();
            if (name == null) return;
            Station selected = Network.findStationById_Name(name);
            if (selected != null) {
                currentStation = selected;
                loadStationData(selected);
                // Enable all fields and buttons
                setFormEnabled(true);
            } else {
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
                    String to = row.toSelected();
                    int weight = row.getWeight();
                        if (weight <= 0) {
                            JOptionPane.showMessageDialog(null, "Route weight must be a positive number.");
                            return;
                        }
                    
                    int toId = Integer.parseInt(to.split("-")[0]);
                    if (toId == currentStation.id) {
                        JOptionPane.showMessageDialog(null, "A station cannot have a route to itself.");
                        return;
                    }
                    // Create route and add to map
                    Route route = new Route(currentStation, Network.findStationById_Name(to), weight,false);
                    updatedRoutes.put(toId, route);
                }
                currentStation.setName(newName);
                Network.deleteallRoutesOfStation(currentStation);
                
                for (Route route : updatedRoutes.values()) {
                    Network.addRoute(route.from.id,route.to.id,route.weight);
                }
                clearForm();
                setFormEnabled(false);
                JOptionPane.showMessageDialog(null, "Station updated successfully!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid weights for routes");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: 404 " + ex.getMessage());
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
                Network.deleteStation(currentStation);
                clearForm();
                setFormEnabled(false);
                JOptionPane.showMessageDialog(null, "Station deleted successfully.");
            }
        });
    }

    private void loadStationData(Station station) {
        nameField.setText(station.name);
        
        // Load existing routes
        routesPanel.removeAll();
        routeRows.clear();
        
        for (Route route : Network.getRoutesOfStation(station)) {
            RouteRow row = new RouteRow(routesPanel, routeRows, route.to.toString(), route.weight);
            routeRows.add(row);
            routesPanel.add(row);
        }
        
        routesPanel.revalidate();
        routesPanel.repaint();
    }

    private void addRouteRow() {
        RouteRow row = new RouteRow(routesPanel, routeRows);
        routeRows.add(row);
        routesPanel.add(row);
        routesPanel.revalidate();
        routesPanel.repaint();
    }

    private void clearForm() {
        isUpdatingForm = true;
        currentStation = null;
        nameField.setText("");
        routesPanel.removeAll();
        routeRows.clear();
        routesPanel.revalidate();
        routesPanel.repaint();
        fillStationComboBox();
        stationCombo.setSelectedIndex(-1);
        stationCombo.setSelectedItem(null);
        isUpdatingForm = false;
    }

    private void fillStationComboBox() {
        stationCombo.removeAllItems();
        for (String item : Network.getStationsID_Name()) {
            stationCombo.addItem(item);
        }
        stationCombo.setSelectedIndex(-1);
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
            row.setEnabled(enabled);
        }
    }

}