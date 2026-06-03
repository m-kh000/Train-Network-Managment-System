package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Fileio;
import logic.Network;
import ui.Manager.Btn;

public class UI extends JFrame {

    private static JPanel centerPanel;
    private Network network;

    public UI(Network network) {
        this.network = network;
        setSize(Manager.SCREEN_WIDTH, Manager.SCREEN_HEIGHT);

        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                network.ExportToFile();
                JOptionPane.showMessageDialog(null, "Data saved to untitled file");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

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
        backButton.setPreferredSize(new Dimension(60, 60));
        backButton.addActionListener(e -> switchContent(new MainPage(network)));
        return backButton;
    }

    public static Network findFileToImport() {
        String filename = showFileChooser();
        if (filename != null && !filename.isEmpty()) {
            return new Network(new File(filename));
        }
        return null;
    }

    private static String showFileChooser() {
        final String[] result = { null };
        JFrame f = new JFrame("pick a file to import");
        JPanel p = new JPanel();
        JPanel btns = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel cbNerr = new JPanel(new GridLayout(2, 1, 0, 0));
        JComboBox<String> cb = new JComboBox<>(new String[10]);
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.defaultFont(false, false));

        Btn importBtn = new Btn("", "import");
        Btn cancelBtn = new Btn("", "cancel");

        cb.setSelectedIndex(-1);
        cb.setFont(Manager.defaultFont(false, false));

        importBtn.addActionListener(e -> {
            if (cb.getSelectedItem() != null) {
                result[0] = cb.getSelectedItem().toString();
                f.dispose();
            } else {
                errorLabel.setText("Please select a file");
                p.revalidate();
            }
        });

        cancelBtn.addActionListener(e -> {
            result[0] = null;
            f.dispose();
        });

        btns.add(importBtn);
        btns.add(cancelBtn);

        cbNerr.add(errorLabel);
        cbNerr.add(cb);

        p.setLayout(new GridLayout(2, 1, 20, 20));
        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        p.add(cbNerr);
        p.add(btns);

        f.add(p);
        f.setBounds(Manager.SCREEN_WIDTH / 2 - 200, Manager.SCREEN_HEIGHT / 2 - 120, 400, 240);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);

        return result[0];
    }

    public static Object typeFileNameToExport(Network network) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeFileNameToExport'");
    }

}