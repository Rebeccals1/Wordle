package view;
import javax.swing.*;
import java.awt.*;
import control.GameController;
import model.WordleModel;
import util.ModelListener;

public class WordleView extends JFrame implements ModelListener {
    private final WordleModel model;
    private GameController controller;
    private final GridPanel grid;
    private final KeyboardPanel keyboard;
    private final JTextField input;

    public WordleView(WordleModel model) {
        this.model = model;
        model.addListener(this);

        setTitle("Wordle Clone");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        grid = new GridPanel(model);
        keyboard = new KeyboardPanel();

        // 🔹 Larger centered input
        input = new JTextField(15);
        input.setFont(new Font("SansSerif", Font.BOLD, 24));
        input.setHorizontalAlignment(JTextField.CENTER);

        JButton guessBtn = new JButton("Guess");
        guessBtn.addActionListener(e -> controller.handleGuess(input.getText()));

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            controller.handleReset();
            keyboard.resetColors();
        });

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> controller.handleSave());

        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> controller.handleLoad());

        JPanel bottomControls = new JPanel();
        bottomControls.add(input);
        bottomControls.add(guessBtn);
        bottomControls.add(resetBtn);
        bottomControls.add(saveBtn);
        bottomControls.add(loadBtn);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(keyboard, BorderLayout.CENTER);
        southPanel.add(bottomControls, BorderLayout.SOUTH);

        add(grid, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setController(GameController c) { this.controller = c; }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    @Override
    public void onModelChanged() {
        grid.repaint();

        var guesses = model.getState().getGuesses();
        var feedbackList = model.getState().getFeedbackList();

        for (int r = 0; r < feedbackList.size(); r++) {
            String guess = guesses.get(r);
            String[] fb = feedbackList.get(r);
            for (int i = 0; i < guess.length(); i++) {
                keyboard.updateKeyColor(guess.charAt(i), fb[i]);
            }
        }
    }
}
