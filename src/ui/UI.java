package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
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

    public static boolean Edited = false;

    private static JPanel centerPanel;
    private Network network;

    // ==================== Constructor ====================

    public UI(Network network) {
        this.network = network;

        // Frame setup
        setSize(Manager.SCREEN_WIDTH, Manager.SCREEN_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Save data on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Fileio.exportToFile(network);
                JOptionPane.showMessageDialog(null, "Data saved to untitled file");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        // Add main page
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MainPage(network));
        add(centerPanel);

        // Frame branding
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

    // Import Methods

    public static Network findFileToImport() {
        String filename = showFileChooser();
        if (filename != null && !filename.isEmpty()) {
            return new Network(new File(filename));
        }
        return null;
    }

    /**
     * Shows a modal dialog with a dropdown of available files to import.
     * 
     * @return Selected filename, or null if cancelled
     */
    private static String showFileChooser() {
        final String[] result = { null };

        // Get available files from Fileio
        String[] files = Fileio.filesNames();
        if (files == null) {
            JOptionPane.showMessageDialog(null, "no files to import", "Oops", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Create modal dialog
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Import File");

        // Error label (shows validation errors)
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        // File dropdown
        JComboBox<String> fileCombo = new JComboBox<>(files);
        fileCombo.setSelectedIndex(-1);
        fileCombo.setFont(Manager.defaultFont(false, false));

        // Dropdown + error panel
        JPanel dropdownPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        dropdownPanel.add(errorLabel);
        dropdownPanel.add(fileCombo);

        // Import button - returns selected file
        Btn importBtn = new Btn("", "Import");
        importBtn.addActionListener(e -> {
            if (fileCombo.getSelectedItem() != null) {
                result[0] = fileCombo.getSelectedItem().toString();
                dialog.dispose();
            } else {
                errorLabel.setText("Please select a file");
            }
        });

        // Cancel button - returns null
        Btn cancelBtn = new Btn("", "Cancel");
        cancelBtn.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(importBtn);
        buttonPanel.add(cancelBtn);

        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.add(dropdownPanel);
        mainPanel.add(buttonPanel);

        // Dialog setup
        dialog.add(mainPanel);
        dialog.setBounds(
                Manager.SCREEN_WIDTH / 2 - 200,
                Manager.SCREEN_HEIGHT / 2 - 120,
                400, 240);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        return result[0];
    }

    public static void typeFileNameToExport(Network network) {

        // Create modal dialog
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Export to File");

        // Error label (shows validation errors)
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        JTextField tf = new JTextField();
        JPanel dropdownPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        dropdownPanel.add(errorLabel);
        dropdownPanel.add(tf);

        Btn exportBtn = new Btn("", "Export");
        exportBtn.addActionListener(e -> {
            String s = tf.getText();
            if(s == null || s.isEmpty()){
                errorLabel.setText("Please type a new file name to export the network to");
                return;
            }
            Fileio.exportToFile(network,s);
        });

        // Cancel button - returns null
        Btn cancelBtn = new Btn("", "Cancel");
        cancelBtn.addActionListener(e ->dialog.dispose());

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(exportBtn);
        buttonPanel.add(cancelBtn);

        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.add(dropdownPanel);
        mainPanel.add(buttonPanel);

        // Dialog setup
        dialog.add(mainPanel);
        dialog.setBounds(Manager.SCREEN_WIDTH / 2 - 200, Manager.SCREEN_HEIGHT / 2 - 120, 400, 240);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

    }

}