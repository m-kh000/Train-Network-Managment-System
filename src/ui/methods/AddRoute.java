package ui.methods;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

import logic.Network;
import ui.Manager;
import ui.Manager.Btn;

public class AddRoute extends JPanel {

    private final JComboBox<String> fromCombo;
    private final JComboBox<String> toCombo;
    private final JTextField weightField;
    private final Btn submitBtn;
    private final String[] names;

    public AddRoute() {
        this.names = Network.getStationsID_Name();

        fromCombo = new JComboBox<>(names);
        toCombo = new JComboBox<>(names);
        weightField = new JTextField();
        submitBtn = new Btn("", "Submit");

        initializeUI();
        initializeListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SIZE, Manager.TP_PADDING_SIZE, Manager.SIDE_PADDING_SIZE));
        add(Manager.topPanel("Add Route"), BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Box.createVerticalStrut(40));

        JPanel fromRow = createRow("From Station:", fromCombo);
        fromCombo.setSelectedIndex(-1);
        fromCombo.setFont(Manager.defaultFont(false, false));
        main.add(fromRow);
        main.add(Box.createVerticalStrut(60));

        JPanel toRow = createRow("To Station:", toCombo);
        toCombo.setSelectedIndex(-1);
        toCombo.setFont(Manager.defaultFont(false, false));
        main.add(toRow);
        main.add(Box.createVerticalStrut(60));

        JPanel weightRow = new JPanel(new BorderLayout(20, 0));
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(Manager.defaultFont(true, false));
        weightLabel.setPreferredSize(new Dimension(150, 30));
        weightField.setFont(Manager.defaultFont(false, false));
        weightRow.add(weightLabel, BorderLayout.WEST);
        weightRow.add(weightField, BorderLayout.CENTER);
        main.add(weightRow);
        main.add(Box.createVerticalStrut(180));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitBtn.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(submitBtn);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createRow(String labelText, JComboBox<String> comboBox) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        JLabel label = new JLabel(labelText);
        label.setFont(Manager.defaultFont(true, false));
        label.setPreferredSize(new Dimension(150, 30));
        row.add(label, BorderLayout.WEST);
        row.add(comboBox, BorderLayout.CENTER);
        return row;
    }

    private void initializeListeners() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                submitBtn.doClick();
            }
        });

        submitBtn.addActionListener(e -> onSubmit());
    }

    private void onSubmit() {
        String weightText = weightField.getText().trim();

        if (fromCombo.getSelectedIndex() == -1 || toCombo.getSelectedIndex() == -1 || weightText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int fromId = parseStationId((String) fromCombo.getSelectedItem());
        int toId = parseStationId((String) toCombo.getSelectedItem());

        if (fromId == toId) {
            JOptionPane.showMessageDialog(this, "A Station cannot have a route to itself.");
            return;
        }

        if (!weightText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive weight.");
            return;
        }

        int weight = Integer.parseInt(weightText);
        if (weight <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter a positive weight.");
            return;
        }

        try {
            Network.addRoute(fromId, toId, weight);
            weightField.setText("");
            fromCombo.setSelectedIndex(-1);
            toCombo.setSelectedIndex(-1);
            JOptionPane.showMessageDialog(this, "Route added successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private int parseStationId(String stationText) {
        return Integer.parseInt(stationText.split("-")[0]);
    }
}