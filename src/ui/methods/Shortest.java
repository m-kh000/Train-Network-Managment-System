package ui.methods;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

import logic.Network;
import logic.Route;
import ui.Manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class Shortest extends JPanel {

    private final JPanel main;
    private final JPanel combos;
    private final JLabel ans = new JLabel();
    private final JLabel cycleLabel = new JLabel();
    private final JComboBox<String> from;
    private final JComboBox<String> to;

    private final Network network;
    private final ShowMap parent;
    private ArrayList<Route> shortest = new ArrayList<>();
    private final String[] names;
    private final Color shortestRoadColor;
    private int dis = -1;

    public Shortest(Network network, ShowMap parent, Color shortestRoadColor) {
        this.network = network;
        this.parent = parent;
        this.shortestRoadColor = shortestRoadColor;
        this.names = network.getStationsID_Name();

        from = new JComboBox<>(names);
        to = new JComboBox<>(names);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Manager.SIDE_SHORTEST, Manager.SIDE_SHORTEST));
        setBackground(Manager.ND_BG);

        main = new JPanel(new FlowLayout());
        main.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        combos = new JPanel(new BorderLayout());
        combos.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        initializeComponents();
        initializeListeners();
        updateCycleLabel();

        add(main, BorderLayout.CENTER);
    }

    private void initializeComponents() {
        JLabel find = new JLabel("Find shortest path between:");
        find.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST - 40, 50));

        from.setSelectedIndex(-1);
        to.setSelectedIndex(-1);
        from.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST / 2 - 30, 30));
        to.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST / 2 - 30, 30));

        ans.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST - 40, 50));
        cycleLabel.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST - 40, 30));
        cycleLabel.setForeground(Color.DARK_GRAY);

        combos.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST, 30));
        combos.add(from, BorderLayout.WEST);
        combos.add(new JLabel(" to "), BorderLayout.CENTER);
        combos.add(to, BorderLayout.EAST);

        main.add(find, BorderLayout.NORTH);
        main.add(combos, BorderLayout.CENTER);
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(ans, BorderLayout.NORTH);
        resultsPanel.add(cycleLabel, BorderLayout.SOUTH);
        main.add(resultsPanel, BorderLayout.SOUTH);
    }

    private void initializeListeners() {
        from.addActionListener(e -> updateShortestPath());
        to.addActionListener(e -> updateShortestPath());

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateShortestPath();
            }
        });
    }

    private void updateShortestPath() {
        if (from.getSelectedIndex() == -1 || to.getSelectedIndex() == -1) {
            ans.setText("");
            parent.updateShortestPath(new ArrayList<>());
            updateCycleLabel();
            return;
        }

        int fromId = Integer.parseInt(((String) from.getSelectedItem()).split("-")[0]);
        int toId = Integer.parseInt(((String) to.getSelectedItem()).split("-")[0]);

        if (fromId == toId) {
            ans.setForeground(Color.RED);
            ans.setText("Choose two different stations.");
            shortest.clear();
            parent.updateShortestPath(shortest);
            updateCycleLabel();
            return;
        }

        dis = network.findShortestPath(fromId, toId, shortest);
        if (shortest == null || shortest.isEmpty()) {
            ans.setForeground(Color.RED);
            ans.setText("No path found!");
        } else {
            ans.setForeground(shortestRoadColor);
            ans.setText("Shortest path distance: " + dis);
        }

        parent.updateShortestPath(shortest);
        updateCycleLabel();
    }

    private void updateCycleLabel() {
        cycleLabel.setText("Graph has cycle: " + (network.hasCycle() ? "Yes" : "No"));
    }
}

