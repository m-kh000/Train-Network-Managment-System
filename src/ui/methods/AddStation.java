package ui.methods;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;

import ui.Manager;
import ui.Manager.Btn;
import ui.components.RouteRow;
import logic.Network;
import logic.Station;
import logic.Route;

/**
 * Panel for adding a new Station to the Network.
 * Provides a simple form to enter the station name and create multiple routes.
 */
public class AddStation extends JPanel {

    private final JTextField nameField = new JTextField();

    private final JPanel routesPanel = new JPanel();
    private final List<RouteRow> routeRows = new ArrayList<>();
    private final Btn addRouteBtn = new Btn(Manager.ADD_PATH, "Add Route", false);
    private final Btn submitBtn = new Btn("", "Submit");

    public AddStation() {
        super(new BorderLayout());

        // Outer padding and header
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SIZE, Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SIZE));
        add(Manager.topPanel("Add Station"), BorderLayout.NORTH);

        // Main form area
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Box.createVerticalStrut(20));

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
        
        //addRouteBtn.setPreferredSize(new Dimension(160, 30));
        addRouteBtn.addActionListener(e -> addRouteRow());
        
        routesHeaderPanel.add(routesLabel, BorderLayout.WEST);
        routesHeaderPanel.add(addRouteBtn, BorderLayout.EAST);
        main.add(routesHeaderPanel);
        main.add(Box.createVerticalStrut(10));

        // --- Routes list (scrollable)
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Routes"));
        JScrollPane scroll = new JScrollPane(routesPanel);
        scroll.setPreferredSize(new Dimension(500, 500));
        main.add(scroll);

        // --- Submit button row
        JPanel buttonRow = new JPanel();
        submitBtn.setPreferredSize(new Dimension(150, 50));

        // Allow Enter to trigger submit from anywhere in this panel
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                submitBtn.doClick();
            }
        });

        submitBtn.addActionListener(e -> onSubmit());
        buttonRow.add(submitBtn);

        add(main, BorderLayout.CENTER);
        add(buttonRow, BorderLayout.SOUTH);
        
        // Clear the routesNumField since we're not using it anymore
        // It was removed from the class
    }

    // Validate inputs, create the Station and its Routes, then clear the form
    private void onSubmit() {
        try {
            // Validate station name field
            String stationName = nameField.getText().trim();
            if (stationName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Station name cannot be empty.");
                return;
            }
            
            // Validate all route rows
            for (RouteRow row : routeRows) {
                if (row.toSelected() == null) {
                    JOptionPane.showMessageDialog(this, "Please select a destination station for all routes.");
                    return;
                }
                
                try {
                    int weight = row.getWeight();
                    if (weight <= 0) {
                        JOptionPane.showMessageDialog(this, "Route weight must be a positive number.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid weight.");
                    return;
                } catch (Exception e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    return;
                }
            }

            // Create station
            Station newStation = new Station(stationName);
            Network.addStation(newStation);
            // Add all routes
            for (RouteRow row : routeRows) {
                String selected = (String) row.toSelected();
                int toId = Integer.parseInt(selected.split("-")[0]);
                int weight = row.getWeight();
                Network.addRoute(newStation.id, toId, weight);
            }

            // Reset form
            nameField.setText("");
            routesPanel.removeAll();
            routeRows.clear();
            routesPanel.revalidate();
            routesPanel.repaint();

            JOptionPane.showMessageDialog(this, "Station added successfully.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    

    private void addRouteRow() {
        RouteRow row = new RouteRow(routesPanel, routeRows);
        routeRows.add(row);
        routesPanel.add(row);
        routesPanel.revalidate();
        routesPanel.repaint();
    }
}