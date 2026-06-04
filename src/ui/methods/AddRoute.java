package ui.methods;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
import ui.Manager.Btn;
import ui.Manager;
import logic.*;

public class AddRoute extends JPanel {

    private JComboBox<String> fromCombo;
    private JComboBox<String> toCombo;
    private JTextField weightField;
    private String[] names;

    public AddRoute(Network network) {

        names = network.getStationsID_Name();
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE));
        add(Manager.topPanel("Add Route",network), BorderLayout.NORTH);

        // Form panel
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(Box.createVerticalStrut(40));
        // From Station 
        JPanel fromRow = new JPanel(new BorderLayout(20, 0));
        JLabel fromLabel = new JLabel("From Station:");
        fromLabel.setFont(Manager.defaultFont(true, false));
        fromLabel.setPreferredSize(new Dimension(150, 30));
        fromCombo = new JComboBox<>(names);
        fromCombo.setFont(Manager.defaultFont(false, false));
        fromRow.setPreferredSize(new Dimension(150, 30));
        fromRow.add(fromLabel, BorderLayout.WEST);
        fromRow.add(fromCombo, BorderLayout.CENTER);
        main.add(fromRow);
        main.add(Box.createVerticalStrut(60));

        // To Station 
        JPanel toRow = new JPanel(new BorderLayout(20, 0));
        JLabel toLabel = new JLabel("To Station:");
        toLabel.setFont(Manager.defaultFont(true, false));
        toLabel.setPreferredSize(new Dimension(150, 30));
        toCombo = new JComboBox<>(names);
        toCombo.setFont(Manager.defaultFont(false, false));
        toRow.add(toLabel, BorderLayout.WEST);
        toRow.add(toCombo, BorderLayout.CENTER);
        main.add(toRow);
        main.add(Box.createVerticalStrut(60));

        // Weight (label + field on one horizontal row)
        JPanel weightRow = new JPanel(new BorderLayout(20, 0));
        JLabel weightLabel = new JLabel("Weight :");
        weightLabel.setFont(Manager.defaultFont(true, false));
        weightLabel.setPreferredSize(new Dimension(150, 30));
        weightField = new JTextField();
        weightField.setFont(Manager.defaultFont(false, false));
        weightRow.add(weightLabel, BorderLayout.WEST);
        weightRow.add(weightField, BorderLayout.CENTER);
        main.add(weightRow);
        main.add(Box.createVerticalStrut(180));

        // Button panel
        JPanel buttonPanel = new JPanel();
        Btn submitBtn = new Btn("","Submit");
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
                String weightText = weightField.getText().trim();

                if (from == null || to == null || weightText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                int weight = Integer.parseInt(weightText);
                from.addRoute(new Route(from, to, weight));

                weightField.setText("");
                JOptionPane.showMessageDialog(null, "Route added successfully.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid weight.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        buttonPanel.add(submitBtn);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}