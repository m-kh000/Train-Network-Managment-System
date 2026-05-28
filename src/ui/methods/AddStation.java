package ui.methods;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ui.Manager;
import ui.UI;
import ui.Manager.*;
import logic.*;

public class AddStation extends JPanel {

    private JTextField nameField;
    private JTextField routesNumField;
    private JPanel routesPanel;
    private java.util.List<RouteRow> routeRows = new java.util.ArrayList<>();
    private Network network;

    public AddStation(Network network) {
        setLayout(new BorderLayout());

        this.network = network;
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Add a Station");
        title.setFont(Manager.defaultFont(true, true));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setPreferredSize(new Dimension(500, 500));

        // Name field
        JPanel namePanel = new JPanel(new BorderLayout(10, 0));
        JLabel nameLabel = new JLabel("Station Name:");
        nameLabel.setFont(Manager.defaultFont(true, false));
        nameLabel.setPreferredSize(new Dimension(150, 30));
        nameField = new JTextField();
        nameField.setFont(Manager.defaultFont(false, false));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Routes number field
        JPanel routesNumPanel = new JPanel(new BorderLayout(10, 0));
        JLabel routesNumLabel = new JLabel("Number of Routes:");
        routesNumLabel.setFont(Manager.defaultFont(true, false));
        routesNumLabel.setPreferredSize(new Dimension(150, 30));
        routesNumField = new JTextField();
        routesNumField.setFont(Manager.defaultFont(false, false));
        
        Btn generateBtn = new Btn("Generate");
        generateBtn.setPreferredSize(new Dimension(120, 30));
        generateBtn.addActionListener(e -> generateRoutes());
        
        routesNumPanel.add(routesNumLabel, BorderLayout.WEST);
        routesNumPanel.add(routesNumField, BorderLayout.CENTER);
        routesNumPanel.add(generateBtn, BorderLayout.EAST);
        formPanel.add(routesNumPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Routes panel with scroll
        routesPanel = new JPanel();
        routesPanel.setLayout(new BoxLayout(routesPanel, BoxLayout.Y_AXIS));
        routesPanel.setBorder(BorderFactory.createTitledBorder("Routes"));
        
        JScrollPane scrollPane = new JScrollPane(routesPanel);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        formPanel.add(scrollPane);

        // Button panel
        JPanel buttonPanel = new JPanel();
        Btn submitBtn = new Btn("Add Station");
        submitBtn.setPreferredSize(new Dimension(150, 50));

        // Enter key functionality
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        getActionMap().put("submit", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                submitBtn.doClick();
            }
        });

        submitBtn.addActionListener(e -> {
            try {
                String stationName = nameField.getText().trim();
                
                if (stationName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a station name.");
                    return;
                }

                Station newStation = new Station(stationName);
                network.addStation(newStation);

                // Add routes from all rows
                for (RouteRow row : routeRows) {
                    Station toStation = (Station) row.toCombo.getSelectedItem();
                    if (toStation != null) {
                        int weight = Integer.parseInt(row.weightField.getText().trim());
                        newStation.addRoute(new Route(newStation, toStation, weight));
                    }
                }

                nameField.setText("");
                routesNumField.setText("");
                routesPanel.removeAll();
                routeRows.clear();
                routesPanel.revalidate();
                routesPanel.repaint();

                JOptionPane.showMessageDialog(null, "Station added successfully.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid route weights.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        buttonPanel.add(submitBtn);

        // Top panel with back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(UI.backBtn(network), BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void generateRoutes() {
        try {
            int num = Integer.parseInt(routesNumField.getText().trim());
            if (num <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
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
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }

    private class RouteRow extends JPanel {
        JComboBox<Station> toCombo;
        JTextField weightField;

        public RouteRow() {
            setLayout(new BorderLayout(10, 0));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel toLabel = new JLabel("To Station:");
            toLabel.setFont(Manager.defaultFont(true, false));
            toLabel.setPreferredSize(new Dimension(100, 30));
            add(toLabel, BorderLayout.WEST);

            toCombo = new JComboBox<>(network.getStations().values().toArray(new Station[0]));
            toCombo.setFont(Manager.defaultFont(false, false));
            add(toCombo, BorderLayout.CENTER);

            weightField = new JTextField();
            weightField.setFont(Manager.defaultFont(false, false));
            weightField.setPreferredSize(new Dimension(100, 30));
            add(weightField, BorderLayout.EAST);
        }
    }
}