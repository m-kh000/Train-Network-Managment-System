package ui.methods;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ui.UI;
import ui.Manager.Btn;
import ui.Manager;
import logic.*;

public class AddRoute extends JPanel {

    private JComboBox<Station> fromCombo;
    private JComboBox<Station> toCombo;
    private JTextField distanceField;
    private Network network;

    public AddRoute(Network network) {
        setLayout(new BorderLayout());

        this.network = network;
        add(Manager.topPanel("Map",network), BorderLayout.NORTH);
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SMALL));

        // Title
        JLabel title = new JLabel("Add a Route");
        title.setFont(Manager.defaultFont(true, true));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setPreferredSize(new Dimension(400, 150));

        // From Station
        JLabel fromLabel = new JLabel("From Station:");
        fromLabel.setFont(Manager.defaultFont(true, false));
        fromCombo = new JComboBox<>(network.getStations().values().toArray(new Station[0]));
        fromCombo.setFont(Manager.defaultFont(false, false));
        
        // To Station
        JLabel toLabel = new JLabel("To Station:");
        toLabel.setFont(Manager.defaultFont(true, false));
        toCombo = new JComboBox<>(network.getStations().values().toArray(new Station[0]));
        toCombo.setFont(Manager.defaultFont(false, false));

        // Distance
        JLabel distanceLabel = new JLabel("Distance (km):");
        distanceLabel.setFont(Manager.defaultFont(true, false));
        distanceField = new JTextField();
        distanceField.setFont(Manager.defaultFont(false, false));

        formPanel.add(fromLabel);
        formPanel.add(fromCombo);
        formPanel.add(toLabel);
        formPanel.add(toCombo);
        formPanel.add(distanceLabel);
        formPanel.add(distanceField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        Btn submitBtn = new Btn("Add Route");
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
                Station from = (Station) fromCombo.getSelectedItem();
                Station to = (Station) toCombo.getSelectedItem();
                String distanceText = distanceField.getText().trim();

                if (from == null || to == null || distanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                int distance = Integer.parseInt(distanceText);
                from.addRoute(new Route(from, to, distance));

                distanceField.setText("");
                JOptionPane.showMessageDialog(null, "Route added successfully.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid distance.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        buttonPanel.add(submitBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}