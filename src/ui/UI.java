package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.Network;
import ui.Manager.Btn;

public class UI extends JFrame {

    private static JPanel centerPanel;

    public UI(Network network) {
        setSize(Manager.SCREEN_WIDTH, Manager.SCREEN_HEIGHT);

        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MainPage(network));

        add(centerPanel);

        ImageIcon icon = new ImageIcon("logo.svg");
        setIconImage(icon.getImage());
        setTitle("'sth' Railways");

        setVisible(true);
    }

    public static void switchContent(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static JButton backBtn(Network network) {
        Btn backButton = new Btn("public/back.png");
        backButton.setBackground(Manager.defaultBGColor());
        backButton.setPreferredSize(new Dimension(60,60));
        backButton.addActionListener(e -> switchContent(new MainPage(network)));
        return backButton;
    }

    
}
