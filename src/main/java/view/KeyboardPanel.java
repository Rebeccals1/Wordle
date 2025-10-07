package view;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class KeyboardPanel extends JPanel {
    private final Map<Character, JButton> keyButtons = new HashMap<>();

    public KeyboardPanel() {
        setLayout(new GridLayout(3, 9, 5, 5));
        setBackground(Color.DARK_GRAY);

        String keys = "QWERTYUIOPASDFGHJKLZXCVBNM";
        for (char c : keys.toCharArray()) {
            JButton key = new JButton(String.valueOf(c));
            key.setFont(new Font("SansSerif", Font.BOLD, 16));
            key.setFocusPainted(false);
            key.setFocusable(false);
            key.setBorderPainted(false);
            key.setBackground(new Color(120, 124, 126));
            key.setForeground(Color.WHITE);
            add(key);
            keyButtons.put(c, key);
        }
    }

    public void updateKeyColor(char letter, String feedback) {
        JButton btn = keyButtons.get(Character.toUpperCase(letter));
        if (btn == null) return;

        Color color = switch (feedback) {
            case "G" -> new Color(83, 141, 78);
            case "Y" -> new Color(181, 159, 59);
            case "X" -> new Color(58, 58, 60);
            default -> btn.getBackground();
        };

        Color current = btn.getBackground();
        if (current.equals(new Color(83,141,78))) return;
        if (current.equals(new Color(181,159,59)) && feedback.equals("X")) return;

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
    }

    public void resetColors() {
        for (JButton b : keyButtons.values()) {
            b.setBackground(new Color(120, 124, 126));
            b.setForeground(Color.WHITE);
        }
    }
}
