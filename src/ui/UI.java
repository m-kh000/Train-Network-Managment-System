package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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

    // ==================== Constructor ====================

    public UI(Network network) {

        // Frame setup
        setSize(Manager.SCREEN_WIDTH, Manager.SCREEN_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //* Save data on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Fileio.exportToFile(network,"files/recent.txt");
                JOptionPane.showMessageDialog(null, "Data saved to recent.txt");
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

    //^ Methods

    //* import methods
    public static void changeNetwork(Network network) {
        Network newnNetwork = UI.findFileToImport();
        if(newnNetwork != null) network = newnNetwork;
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

        JLabel l = new JLabel("Select file to import:                                                                                 .   ", JLabel.LEFT);
        l.setFont(Manager.defaultFont(false, false));
        // Error label (shows validation errors)
        JLabel errorLabel = new JLabel(".                                                                                 .   ");
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        // File dropdown
        JComboBox<String> fileCombo = new JComboBox<>(files);
        fileCombo.setSelectedIndex(-1);
        fileCombo.setFont(Manager.defaultFont(false, false));

        // Dropdown + error panel
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(l);
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
                Manager.SCREEN_WIDTH / 2 - 210,
                Manager.SCREEN_HEIGHT / 2 - 130,
                420, 260);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        return result[0];
    }

    //* export method
    public static void typeFileNameToExport(Network network) {

        // Create modal dialog
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Export to File");

        JLabel l = new JLabel("File name:                                                                                                                                         .      ",JLabel.LEFT);
        l.setFont(Manager.defaultFont(false, false));

        // Error label (shows validation errors)
        JLabel errorLabel = new JLabel(".                                                                                                                                         .      ",JLabel.LEFT);
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(Manager.hintFont());

        JTextField tf = new JTextField();
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.add(l);
        dropdownPanel.add(errorLabel);
        dropdownPanel.add(tf);

        Btn exportBtn = new Btn("", "Export");
        exportBtn.addActionListener(e -> {
            String s = tf.getText();
            if(s == null || s.isEmpty()){
                errorLabel.setText("Please type a new file name to export the network to");
                return;
            }
            Fileio.exportToFile(network,"files/"+s+LocalDate.now()+".txt");
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        mainPanel.add(dropdownPanel);
        mainPanel.add(buttonPanel);

        // Dialog setup
        dialog.add(mainPanel);
        dialog.setBounds(
                Manager.SCREEN_WIDTH / 2 - 210,
                Manager.SCREEN_HEIGHT / 2 - 130,
                420, 260);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

    }

}