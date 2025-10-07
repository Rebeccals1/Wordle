package view;
import model.WordleModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridPanel extends JPanel {
    private final WordleModel model;

    public GridPanel(WordleModel model) {
        this.model = model;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int box = 65;
        int spacing = 8;
        int totalWidth = 5 * box + 4 * spacing;
        int totalHeight = 6 * box + 5 * spacing;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - totalHeight) / 2;

        List<String> guesses = model.getState().getGuesses();
        List<String[]> feedbackList = model.getState().getFeedbackList();

        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                int x = startX + c * (box + spacing);
                int y = startY + r * (box + spacing);
                Color tileColor = Color.LIGHT_GRAY;

                if (r < feedbackList.size()) {
                    String fb = feedbackList.get(r)[c];
                    switch (fb) {
                        case "G" -> tileColor = new Color(83, 141, 78);
                        case "Y" -> tileColor = new Color(181, 159, 59);
                        case "X" -> tileColor = new Color(58, 58, 60);
                    }
                }

                g2.setColor(tileColor);
                g2.fillRect(x, y, box, box);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, box, box);

                if (r < guesses.size()) {
                    String guess = guesses.get(r);
                    if (c < guess.length()) {
                        String letter = String.valueOf(guess.charAt(c));
                        int textWidth = fm.stringWidth(letter);
                        int textHeight = fm.getAscent();
                        int textX = x + (box - textWidth) / 2;
                        int textY = y + (box + textHeight) / 2 - 4;
                        g2.setColor(Color.WHITE);
                        g2.drawString(letter, textX, textY);
                    }
                }
            }
        }
    }
}
