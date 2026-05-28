package ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.Manager.Btn;

public class UI extends JFrame {

    private static JPanel centerPanel;

    public UI() {
        setSize(
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height - 1
        );

        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel fullPanel = new JPanel(new BorderLayout());
        fullPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 80, 20));

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MainPage());

        fullPanel.add(centerPanel);
        add(fullPanel);

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

    public static JButton backBtn() {
        Btn backButton = new Btn("←");
        backButton.addActionListener(e -> switchContent(new MainPage()));
        return backButton;
    }
}
