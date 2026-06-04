package ui.methods;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;

import ui.Manager;
import ui.Manager.Btn;
import logic.Network;
import logic.Station;
import logic.Route;

/**
 * Panel for adding a new Station to the Network.
 * Provides a simple form to enter the station name and create multiple routes.
 */
public class AddStation extends JPanel {

    private final Network network;
    private final JTextField nameField = new JTextField();
    private final JTextField routesNumField = new JTextField();
    private final JPanel routesPanel = new JPanel();
    private final List<RouteRow> routeRows = new ArrayList<>();
    private String[] names;

    public AddStation(Network network) {
        super(new BorderLayout());
        this.network = network;
        names = network.getStationsID_Name();

        // Outer padding and header
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,
                Manager.SIDE_PADDING_SIZE, Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SIZE));
        add(Manager.topPanel("Add Station", network), BorderLayout.NORTH);

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

        // --- Routes count row with generate button
        JPanel routesNumRow = new JPanel(new BorderLayout(10, 0));
        JLabel routesNumLabel = new JLabel("Number of Routes:");
        routesNumLabel.setFont(Manager.defaultFont(true, false));
        routesNumLabel.setPreferredSize(new Dimension(150, 30));
        routesNumField.setFont(Manager.defaultFont(false, false));

        Btn generateBtn = new Btn("", "Add Routes",false);
        generateBtn.setPreferredSize(new Dimension(120, 30));
        generateBtn.addActionListener(e -> generateRoutes());

        routesNumRow.add(routesNumLabel, BorderLayout.WEST);
        routesNumRow.add(routesNumField, BorderLayout.CENTER);
        routesNumRow.add(generateBtn, BorderLayout.EAST);
        main.add(routesNumRow);
        main.add(Box.createVerticalStrut(20));

        // --- Routes list (scrollable)
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Routes"));
        JScrollPane scroll = new JScrollPane(routesPanel);
        scroll.setPreferredSize(new Dimension(500, 500));
        main.add(scroll);

        // --- Submit button row
        JPanel buttonRow = new JPanel();
        Btn submitBtn = new Btn("","Submit");
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
    }

    // Validate inputs, create the Station and its Routes, then clear the form
    private void onSubmit() {
        try {
            String stationName = nameField.getText().trim();
            if (stationName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a station name.");
                return;
            }

            Station newStation = new Station(stationName);
            network.addStation(newStation);

            for (RouteRow row : routeRows) {
                Station toStation = (Station) row.toCombo.getSelectedItem();
                if (toStation != null) {
                    int weight = Integer.parseInt(row.weightField.getText().trim());
                    newStation.addRoute(new Route(newStation, toStation, weight));
                }
            }

            // Reset form
            nameField.setText("");
            routesNumField.setText("");
            routesPanel.removeAll();
            routeRows.clear();
            routesPanel.revalidate();
            routesPanel.repaint();

            JOptionPane.showMessageDialog(this, "Station added successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric weights for routes.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Generates the specified number of RouteRow entries
    private void generateRoutes() {
        try {
            int num = Integer.parseInt(routesNumField.getText().trim());
            if (num <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.");
                return;
            }

            routesPanel.removeAll();
            routeRows.clear();

            for (int i = 0; i < num; i++) {
                RouteRow row = new RouteRow();
                routeRows.add(row);
                routesPanel.add(row);
            }

            routesPanel.revalidate();
            routesPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    /**
     * Single-row component for selecting a destination station and weight.
     */
    private class RouteRow extends JPanel {
        final JComboBox<String> toCombo;
        final JTextField weightField = new JTextField();

        RouteRow() {
            super(new BorderLayout(10, 0));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel toLabel = new JLabel("To Station:");
            toLabel.setFont(Manager.defaultFont(true, false));
            toLabel.setPreferredSize(new Dimension(100, 30));
            add(toLabel, BorderLayout.WEST);

            toCombo = new JComboBox<>(names);
            toCombo.setFont(Manager.defaultFont(false, false));
            add(toCombo, BorderLayout.CENTER);

            JPanel weightRow = new JPanel(new BorderLayout(10, 0));
            JLabel weightLabel = new JLabel("Weight :");
            weightLabel.setFont(Manager.defaultFont(false, false));
            weightLabel.setPreferredSize(new Dimension(70, 30));
            weightField.setFont(Manager.defaultFont(false, false));
            weightRow.setPreferredSize(new Dimension(160, 30));
            weightRow.add(weightLabel, BorderLayout.WEST);
            weightRow.add(weightField, BorderLayout.CENTER);
            add(weightRow, BorderLayout.EAST);
        }
    }
}