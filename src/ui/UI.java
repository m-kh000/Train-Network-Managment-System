package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Fileio;
import logic.Network;
import ui.Manager.Btn;

public class UI extends JFrame {

    private static JPanel centerPanel;
    private static Network currentNetwork;

    public UI(Network network) {
        currentNetwork = network;
        initializeFrame();
        initializeContent();
    }

    private void initializeFrame() {
        setSize(Manager.SCREEN_WIDTH, Manager.SCREEN_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //TODO Fileio.exportToFile(currentNetwork, "files/recent.txt");
                JOptionPane.showMessageDialog(null, "Data saved to recent.txt");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        ImageIcon icon = new ImageIcon("logo.svg");
        setIconImage(icon.getImage());
        setTitle("'sth' Railways");
    }

    private void initializeContent() {
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MainPage(currentNetwork), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void switchContent(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel, BorderLayout.CENTER);
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

    public static void changeNetwork() {
        Network newNetwork = findFileToImport();
        if (newNetwork != null) {
            currentNetwork = newNetwork;
            switchContent(new MainPage(currentNetwork));
        }
    }

    public static Network findFileToImport() {
        String filename = showFileChooser();
        if (filename != null && !filename.isEmpty()) {
            return new Network("/files/" + filename);
        }
        return null;
    }

    private static String showFileChooser() {
        final String[] result = { null };

        String[] files = Fileio.TxtFiles().toArray(new String[0]);
        if (files.length == 0) {
            JOptionPane.showMessageDialog(null, "No files to import", "Oops", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Import File");

        JLabel label = new JLabel("Select file to import:", JLabel.LEFT);
        label.setFont(Manager.defaultFont(false, false));

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        JComboBox<String> fileCombo = new JComboBox<>(files);
        fileCombo.setSelectedIndex(-1);
        fileCombo.setFont(Manager.defaultFont(false, false));

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(label);
        dropdownPanel.add(errorLabel);
        dropdownPanel.add(fileCombo);

        Btn importBtn = new Btn("", "Import");
        importBtn.addActionListener(e -> {
            if (fileCombo.getSelectedItem() != null) {
                result[0] = fileCombo.getSelectedItem().toString();
                dialog.dispose();
            } else {
                errorLabel.setText("Please select a file");
            }
        });

        Btn cancelBtn = new Btn("", "Cancel");
        cancelBtn.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(importBtn);
        buttonPanel.add(cancelBtn);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.add(dropdownPanel);
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);
        dialog.setBounds(
                Manager.SCREEN_WIDTH / 2 - 210,
                Manager.SCREEN_HEIGHT / 2 - 130,
                420, 260);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        return result[0];
    }

    public static void typeFileNameToExport(Network network) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Export to File");

        JLabel label = new JLabel("File name:", JLabel.LEFT);
        label.setFont(Manager.defaultFont(false, false));

        JLabel errorLabel = new JLabel(" ", JLabel.LEFT);
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        JTextField tf = new JTextField();
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(label);
        dropdownPanel.add(errorLabel);
        dropdownPanel.add(tf);

        Btn exportBtn = new Btn("", "Export");
        exportBtn.addActionListener(e -> {
            String s = tf.getText();
            if (s == null || s.isEmpty()) {
                errorLabel.setText("Please type a new file name to export the network to");
                return;
            }
            //TODO Fileio.exportToFile(network, "files/" + s + LocalDate.now() + ".txt");
        });

        Btn cancelBtn = new Btn("", "Cancel");
        cancelBtn.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(exportBtn);
        buttonPanel.add(cancelBtn);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        mainPanel.add(dropdownPanel);
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);
        dialog.setBounds(
                Manager.SCREEN_WIDTH / 2 - 210,
                Manager.SCREEN_HEIGHT / 2 - 130,
                420, 260);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}