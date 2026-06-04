package ui.methods;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import ui.UI;
import ui.Manager;
import ui.Manager.Btn;
import logic.*;

public class EditStation extends JPanel {

    private Network network;
    private JComboBox<Station> stationCombo;
    private JTextField nameField;
    private JPanel routesPanel;
    private Btn addRouteBtn;
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
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Title panel
        mainPanel.add(Manager.topPanel("Edit Station", network));
        mainPanel.add(Box.createVerticalStrut(20));

        // Select station panel
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel selectLabel = new JLabel("Select Station:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        Station[] stations = network.getStations().values().toArray(new Station[0]);
        stationCombo = new JComboBox<>(stations);
        stationCombo.setSelectedItem(null);
        stationCombo.setFont(Manager.defaultFont(false, false));
        
        selectPanel.add(selectLabel);
        selectPanel.add(stationCombo);
        mainPanel.add(selectPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Station name field
        JPanel namePanel = new JPanel(new BorderLayout(10, 0));
        JLabel nameLabel = new JLabel("Station Name:");
        nameLabel.setFont(Manager.defaultFont(true, false));
        nameLabel.setPreferredSize(new Dimension(150, 30));
        nameField = new JTextField();
        nameField.setFont(Manager.defaultFont(false, false));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Routes management panel
        JPanel routesHeaderPanel = new JPanel(new BorderLayout());
        JLabel routesLabel = new JLabel("Routes:");
        routesLabel.setFont(Manager.defaultFont(true, false));
        
        addRouteBtn = new Btn(Manager.ADD_PATH, "Add Route");
        addRouteBtn.setPreferredSize(new Dimension(140, 35));
        addRouteBtn.addActionListener(e -> {
            if (currentStation != null) {
                addRouteRow();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a station first.");
            }
        });
        
        routesHeaderPanel.add(routesLabel, BorderLayout.WEST);
        routesHeaderPanel.add(addRouteBtn, BorderLayout.EAST);
        mainPanel.add(routesHeaderPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Routes panel with scroll
        routesPanel = new JPanel();
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Current Routes"));
        
        JScrollPane scrollPane = new JScrollPane(routesPanel);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(20));

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        submitBtn = new Btn("", "Save Changes");
        discardBtn = new Btn("", "Discard");
        deleteStationBtn = new Btn("", "Delete Station");
        deleteStationBtn.setBackground(new Color(220, 60, 60));
        
        submitBtn.setPreferredSize(new Dimension(150, 40));
        discardBtn.setPreferredSize(new Dimension(120, 40));
        deleteStationBtn.setPreferredSize(new Dimension(140, 40));

        actionPanel.add(submitBtn);
        actionPanel.add(discardBtn);
        actionPanel.add(deleteStationBtn);
        mainPanel.add(actionPanel);

        add(mainPanel, BorderLayout.CENTER);

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

        // Initially disable form (no station selected)
        setFormEnabled(false);

        // Submit button - save all changes
        submitBtn.addActionListener(e -> {
            if (currentStation == null) {
                JOptionPane.showMessageDialog(null, "Please select a station first.");
                return;
            }
            
            try {
                // Update station name if changed
                String newName = nameField.getText().trim();
                if (!newName.isEmpty() && !newName.equals(currentStation.name)) {
                    currentStation.setName(newName);
                }
                
                // Update routes
                for (RouteRow row : routeRows) {
                    if (row.toCombo.getSelectedItem() != null) {
                        Station to = (Station) row.toCombo.getSelectedItem();
                        String weightText = row.weightField.getText().trim();
                        
                        if (!weightText.isEmpty()) {
                            int weight = Integer.parseInt(weightText);
                            boolean isNewRoute = row.getRouteId() == null;
                            
                            if (isNewRoute) {
                                // Add new route
                                currentStation.addRoute(new Route(currentStation, to, weight));
                            } else {
                                // Update existing route
                                Route existingRoute = currentStation.getRoute(to.id);
                                if (existingRoute != null) {
                                    existingRoute.weight = weight;
                                }
                            }
                        }
                    }
                }
                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Station updated successfully!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid route weights.");
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
                // TODO: Remove station from network
                JOptionPane.showMessageDialog(null, "Station deleted (functionality to be implemented).");
                stationCombo.removeItem(currentStation);
                clearForm();
                setFormEnabled(false);
            }
        });
    }

    private void loadStationData(Station station) {
        nameField.setText(station.name);
        
        // Load existing routes
        routesPanel.removeAll();
        routeRows.clear();
        
        for (Route route : station.getRouts().values()) {
            RouteRow row = new RouteRow(route.to, route.weight);
            routeRows.add(row);
            routesPanel.add(row);
        }
        
        routesPanel.revalidate();
        routesPanel.repaint();
    }

    private void addRouteRow() {
        RouteRow row = new RouteRow();
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
            row.toCombo.setEnabled(enabled);
            row.weightField.setEnabled(enabled);
            row.deleteBtn.setEnabled(enabled);
        }
    }

    // Inner class for route rows
    private class RouteRow extends JPanel {
        JComboBox<Station> toCombo;
        JTextField weightField;
        Btn deleteBtn;
        Integer routeId; // null for new routes
        boolean isExistingRoute;
        
        public RouteRow() {
            this(null, 0);
        }
        
        public RouteRow(Station to, int weight) {
            this.isExistingRoute = (to != null);
            setLayout(new BorderLayout(10, 0));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            
            // To Station dropdown
            JLabel toLabel = new JLabel("To:");
            toLabel.setFont(Manager.defaultFont(true, false));
            toLabel.setPreferredSize(new Dimension(50, 30));
            add(toLabel, BorderLayout.WEST);
            
            toCombo = new JComboBox<>(network.getStations().values().toArray(new Station[0]));
            toCombo.setFont(Manager.defaultFont(false, false));
            if (to != null) {
                toCombo.setSelectedItem(to);
            }
            add(toCombo, BorderLayout.CENTER);
            
            // Weight field
            JPanel weightPanel = new JPanel(new BorderLayout(5, 0));
            JLabel weightLabel = new JLabel("Weight:");
            weightLabel.setFont(Manager.defaultFont(true, false));
            weightLabel.setPreferredSize(new Dimension(70, 30));
            
            weightField = new JTextField();
            weightField.setFont(Manager.defaultFont(false, false));
            weightField.setPreferredSize(new Dimension(80, 30));
            // Fill if old route, empty if new
            weightField.setText(isExistingRoute ? String.valueOf(weight) : "");
            
            weightPanel.add(weightLabel, BorderLayout.WEST);
            weightPanel.add(weightField, BorderLayout.CENTER);
            add(weightPanel, BorderLayout.EAST);
            
            // Delete button for ALL routes (red X)
            deleteBtn = new Btn("", "X");
            deleteBtn.setPreferredSize(new Dimension(40, 30));
            deleteBtn.setBackground(new Color(220, 60, 60));
            deleteBtn.addActionListener(e -> {
                routesPanel.remove(this);
                routeRows.remove(this);
                routesPanel.revalidate();
                routesPanel.repaint();
            });
            add(deleteBtn, BorderLayout.AFTER_LINE_ENDS);
        }
        
        public Integer getRouteId() {
            return routeId;
        }
    }
}