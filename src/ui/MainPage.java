package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ui.Manager.Btn;
import ui.methods.AddRoute;
import ui.methods.AddStation;
import ui.methods.EditRoute;
import ui.methods.EditStation;

public class MainPage extends JPanel {

    public MainPage() {
        setLayout(new BorderLayout());

        JLabel logo = new JLabel();
        ImageIcon icon = new ImageIcon(
            new ImageIcon("public/logo.png")
                .getImage()
                .getScaledInstance(190, 190, Image.SCALE_DEFAULT)
        );

        logo.setIcon(icon);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.TOP);

        add(logo, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 180, 0, 180));

        Btn btnStations = new Btn("public/station.png", "Our Stations");
        centerPanel.add(btnStations);

        Btn btnMap = new Btn("public/map1.png", "Show Map");
        centerPanel.add(btnMap);

        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 10));
        Btn btnAddStation = new Btn("public/add.png", "Add Station");
        btnAddStation.addActionListener(e -> UI.switchContent(new AddStation()));
        Btn btnAddRoute = new Btn("public/add.png", "Add Route");
        btnAddRoute.addActionListener(e -> UI.switchContent(new AddRoute()));
        row3.add(btnAddStation);
        row3.add(btnAddRoute);
        centerPanel.add(row3);

        JPanel row4 = new JPanel(new GridLayout(1, 2, 10, 10));
        Btn btnEditStation = new Btn("public/edit.png", "Edit Station");
        btnEditStation.addActionListener(e -> UI.switchContent(new EditStation()));
        Btn btnEditRoute = new Btn("public/edit.png", "Edit Route");
        btnEditRoute.addActionListener(e -> UI.switchContent(new EditRoute()));
        row4.add(btnEditStation);
        row4.add(btnEditRoute);
        centerPanel.add(row4);

        add(centerPanel, BorderLayout.CENTER);

        JLabel paddingWest = new JLabel();
        JLabel paddingEast = new JLabel();
        paddingWest.setPreferredSize(new Dimension(200, 200));
        paddingEast.setPreferredSize(new Dimension(200, 200));

        add(paddingWest, BorderLayout.WEST);
        add(paddingEast, BorderLayout.EAST);
    }
}
/*
1. Switch to your branch B:

git checkout B

2. Pull latest from main (to sync with latest changes):

git fetch origin
git pull origin main

3. Make your changes, then add and commit:

git add .
git commit -m "Your commit message here"

4. Push your branch to GitHub:

git push origin B

5. Merge B into main (if on main branch):

git checkout main
git merge B

6. If conflicts occur, check conflicted files:

git status

7. Resolve each conflict in VSCode, then:

git add .
git commit -m "Resolved merge conflicts"

8. Push the merge:

git push origin main
*/ 