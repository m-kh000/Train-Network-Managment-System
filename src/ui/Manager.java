package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Manager {

    private static final String FONT_NAME = "Arial";
    private static final int NORMAL_SIZE = 16;
    private static final int BIG_SIZE = 20;
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 250);
    private static final Color BUTTON_COLOR = new Color(190, 110, 200);

    public static boolean isEdited = false;
    public static boolean taskAutorefresh = false;
    public static boolean plAutorefresh = false;
    public static boolean itemAutorefresh = false;

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
            setForeground(Color.WHITE);

            ImageIcon icon = new ImageIcon(
                new ImageIcon(iconPath)
                    .getImage()
                    .getScaledInstance(30, 30, Image.SCALE_DEFAULT)
            );

            setIcon(icon);
            setHorizontalAlignment(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.LEFT);
            setIconTextGap(30);
        }

        public Btn(String iconPath) {
            this(iconPath, "");
        }
    }
}
