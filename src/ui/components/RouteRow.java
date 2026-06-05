package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Network;
import logic.Station;
import ui.Manager;
import ui.Manager.Btn;

public class RouteRow extends JPanel {
    JComboBox<String> toCombo;
    JTextField weightField;
    Btn deleteBtn;
    Integer routeId; // null for new routes
    boolean isExistingRoute;
    Network network;

    public RouteRow(Network n, JPanel routesPanel, List<RouteRow> routeRows) {
        this(n, routesPanel, routeRows, null, 0);
    }

    public RouteRow(Network n, JPanel routesPanel, List<RouteRow> routeRows, String to, int weight) {
        this.network = n;
        this.isExistingRoute = (to != null);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        setBorder(BorderFactory.createEmptyBorder(10, 9, 10, 9));

        // To Station dropdown
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(Manager.defaultFont(true, false));
        toLabel.setPreferredSize(new Dimension(45, 45));
        add(toLabel);
        add(Box.createHorizontalStrut(10));

        toCombo = new JComboBox<>(network.getStationsID_Name());
        toCombo.setPreferredSize(new Dimension(120, 45));
        toCombo.setFont(Manager.defaultFont(false, false));
        toCombo.setSelectedItem(to);
        add(toCombo);
        add(Box.createHorizontalStrut(30));

        // Weight field
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel,BoxLayout.X_AXIS));
        weightPanel.setPreferredSize(new Dimension(120, 45));
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(Manager.defaultFont(true, false));
        weightLabel.setPreferredSize(new Dimension(65, 45));

        weightField = new JTextField();
        weightField.setFont(Manager.defaultFont(false, false));
        weightField.setPreferredSize(new Dimension(60, 45));
        // Fill if old route, empty if new
        weightField.setText(isExistingRoute ? String.valueOf(weight) : "");

        weightPanel.add(weightLabel);
        add(Box.createHorizontalStrut(10));
        weightPanel.add(weightField);
        add(weightPanel);
        add(Box.createHorizontalStrut(80));

        // Delete button
        deleteBtn = new Btn("", "X");
        deleteBtn.setPreferredSize(new Dimension(45, 45));
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

    public Object toSelected() {
        return toCombo.getSelectedItem();
    }

    public String getWeight() {
        return weightField.getText();
    }
    
    public void setEnabled(boolean enabled) {
        toCombo.setEnabled(enabled);
        weightField.setEnabled(enabled);
        deleteBtn.setEnabled(enabled);
    }
}
