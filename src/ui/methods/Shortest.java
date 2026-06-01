package ui.methods;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Network;
import logic.Route;
import ui.Manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class Shortest extends JPanel {
    // UI components
    private final JPanel main;
    private final JPanel combos;
    private final JLabel ans = new JLabel();
    private final JComboBox<String> from;
    private final JComboBox<String> to;

    // Data and parent reference
    private final Network network;
    private final ShowMap parent;
    private ArrayList<Route> shortest;
    private final String[] names;
    private final Color shortestRoadColor;

    public Shortest(Network network, ShowMap parent, Color shortestRoadColor) {
        this.network = network;
        this.parent = parent;
        this.shortestRoadColor = shortestRoadColor;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Manager.SIDE_SHORTEST, Manager.SIDE_SHORTEST));
        setBackground(Manager.ND_BG);

        main = new JPanel(new FlowLayout());
        main.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        combos = new JPanel(new BorderLayout());
        combos.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));

        JLabel find = new JLabel("Find shortest path between:");
        find.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST-40, 50));

        names = network.getStationsID_NameArr();

        from = new JComboBox<>(names);
        to = new JComboBox<>(names);
        from.setSelectedItem(null);
        to.setSelectedItem(null);
        from.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST/2 -30 , 30));
        to.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST/2 -30, 30));
        ans.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST-40, 50));

        from.addActionListener(e -> updateShortestPath());
        to.addActionListener(e -> updateShortestPath());

        combos.setPreferredSize(new Dimension(Manager.SIDE_SHORTEST, 30));
        combos.add(from,BorderLayout.WEST);
        combos.add(new JLabel(" to "),BorderLayout.CENTER);
        combos.add(to,BorderLayout.EAST);

        main.add(find);
        main.add(combos);
        main.add(ans);
        add(main, BorderLayout.CENTER);
    }

    private void updateShortestPath() {
        int fromint = Integer.valueOf(names[from.getSelectedIndex()].split("-")[0]);
        int toint = Integer.valueOf(names[to.getSelectedIndex()].split("-")[0]);
        if(fromint != -1 && toint != -1){
            shortest = network.findShortestPath(fromint, toint);
            if(shortest.isEmpty()){
                ans.setForeground(Color.RED);
                ans.setText("No path found!");
            }
            else{
                ans.setForeground(shortestRoadColor);
                ans.setText("Shortest path distance: " + "");
            }
            revalidate();
            repaint();
            parent.updateShortestPath(shortest);
        }
    }
}
