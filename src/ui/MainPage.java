package ui;

import java.awt.BorderLayout;
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
import ui.methods.EditStation;
import ui.methods.ShowMap;
import ui.methods.ShowStations;

public class MainPage extends JPanel {
    public MainPage() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE,Manager.TP_PADDING_SIZE,Manager.SIDE_PADDING_SIZE));

        // Logo
        JLabel logo = new JLabel();
        ImageIcon icon = new ImageIcon(
            new ImageIcon(Manager.LOGO_PATH)
                .getImage()
                .getScaledInstance(Manager.LOGO_WIDTH, Manager.LOGO_WIDTH, Image.SCALE_DEFAULT)
        );
        logo.setIcon(icon);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.TOP);
        add(logo, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 0, Manager.ROW_SPACING));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(Manager.TP_PADDING_SIZE/2, 0,0,0));

        // Row 1: Our Stations
        Btn btnStations = new Btn(Manager.STATION_PATH, "Our Stations");
        centerPanel.add(btnStations);
        btnStations.addActionListener(e -> UI.switchContent(new ShowStations()));

        // Row 2: Show Map
        Btn btnMap = new Btn(Manager.MAP_PATH, "Show Map");
        centerPanel.add(btnMap);
        btnMap.addActionListener(e -> UI.switchContent(new ShowMap()));

        // Row 3: Add Station | Add Route
        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 10));
        Btn btnAddStation = new Btn(Manager.ADD_PATH, "Add Station");
        btnAddStation.addActionListener(e -> UI.switchContent(new AddStation()));
        Btn btnAddRoute = new Btn(Manager.ADD_PATH, "Add Route");
        btnAddRoute.addActionListener(e -> UI.switchContent(new AddRoute()));
        row3.add(btnAddStation);
        row3.add(btnAddRoute);
        centerPanel.add(row3);

        // Row 4: Edit Stations and routes
        Btn btnEditStation = new Btn(Manager.EDIT_PATH, "Edit Stations and Routes");
        btnEditStation.addActionListener(e -> UI.switchContent(new EditStation()));
        centerPanel.add(btnEditStation);


        // Row5 import from file | Export to file
        JPanel row5 = new JPanel(new GridLayout(1, 2, 10, 10));
        Btn btnImport = new Btn(Manager.EDIT_PATH, "Import File");
        btnImport.addActionListener(e -> UI.changeNetwork());
        Btn btnExport = new Btn(Manager.EDIT_PATH, "Export To File");
        btnExport.addActionListener(e -> UI.typeFileNameToExport());
        row5.add(btnImport);
        row5.add(btnExport);
        centerPanel.add(row5);

        add(centerPanel, BorderLayout.CENTER);
    }
}
/*
# =========================
# BEFORE WORKING
# =========================

# go to main branch
git checkout main

# get latest changes from github
git pull origin main

# switch to your personal branch
git checkout MK

# update your branch with latest main
git merge main

# now start coding normally...



# =========================
# AFTER YOU FINISH WORKING
# =========================

# check changed files
git status

# add changes
git add .

# commit changes
git commit -m "Describe what you changed"

# push your branch to github
git push origin MK



# =========================
# MERGING YOUR WORK INTO MAIN
# =========================

# switch to main
git checkout main

# make sure main is updated
git pull origin main

# merge your branch into main
git merge MK

# push updated main to github
git push origin main

# go to main branch
git checkout MK
*/