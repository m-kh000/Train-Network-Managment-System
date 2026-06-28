package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Manager {
 
    private static final String FONT_NAME = "Georgia" ;
    private static final int NORMAL_SIZE = 16;
    private static final int BIG_SIZE = 24;
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 250);
    public  static final Color BUTTON_COLOR = new Color(240, 190, 80);
    public  static final Color BUTTON_TEXT = new Color(255, 255, 255);
    public  static final Color BUTTON_OUTLINE = new Color(208, 145, 130);
    private static final Color HOVER_BORDER_COLOR = new Color(216, 90, 59);

    public static boolean isEdited = false;
    public static boolean taskAutorefresh = false;
    public static boolean plAutorefresh = false;
    public static boolean itemAutorefresh = false;

    // main page

    public static final String LOGO_PATH = "public/train2.png";
    public static final String STATION_PATH = "public/pin.png";
    public static final String MAP_PATH = "public/map.png";
    public static final String ADD_PATH = "public/add.png";
    public static final String EDIT_PATH = "public/edit.png";

    public static final int LOGO_WIDTH = 240;
    public static final int ICON_SIZE = 30;
    public static final int BTN_ICON_GAP = 30;
    public static final int ROW_SPACING = 20;
    public static final int SIDE_PADDING_SIZE = 380;
    public static final int TP_PADDING_SIZE = 40;
    public static final int SIDE_PADDING_SMALL = 220;

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    public static final int SCREEN_HEIGHT = SCREEN_SIZE.height - 1;
    public static final int SIDE_SHORTEST = 350;
    public static final Color ND_BG = Color.WHITE;

    public static Color validColor(int red, int green, int blue) {
        return new Color(clampColorValue(red), clampColorValue(green), clampColorValue(blue));
    }

    private static int clampColorValue(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public static Font defaultFont(boolean isBold, boolean isBig) {
        int style = isBold ? Font.BOLD : Font.PLAIN;
        int size = isBig ? BIG_SIZE : NORMAL_SIZE;
        return new Font(FONT_NAME, style, size);
    }

    public static Font hintFont() {
        return new Font(FONT_NAME, Font.PLAIN, 10);
    }

    public static Color defaultBGColor() {
        return BACKGROUND_COLOR;
    }

    public static class Btn extends JButton {

        public Btn(String iconPath, String text) {
            super(text);
            setFocusPainted(false);
            setFont(defaultFont(true, true));
            setBackground(BUTTON_COLOR);
            setForeground(BUTTON_TEXT);

            if (!iconPath.equals("")) {
                ImageIcon icon = new ImageIcon(
                        new ImageIcon(iconPath)
                                .getImage()
                                .getScaledInstance(30, 30, Image.SCALE_DEFAULT));

                setIcon(icon);
                setIconTextGap(30);
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.LEFT);
            setBorder(BorderFactory.createLineBorder(BUTTON_OUTLINE, 1));
            setOpaque(true);

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(HOVER_BORDER_COLOR, 1));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(BUTTON_OUTLINE, 1));
                }
            });
        }

        public Btn(String iconPath) {
            this(iconPath, "");
        }

        public Btn(String iconPath, String text, boolean big) {
            this(iconPath,text);
            setFont(defaultFont(true, big));
        }

    }

    public static JPanel topPanel(String string) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JLabel title = new JLabel(string + "      ", JLabel.CENTER);
        title.setFont(defaultFont(true, true));
        panel.setPreferredSize(new Dimension(60, 60));
        panel.add(UI.backBtn(), BorderLayout.WEST);
        panel.add(title, BorderLayout.CENTER);
        return panel;
    }
}
