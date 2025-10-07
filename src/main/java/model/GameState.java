package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String secretWord;
    private final List<String> guesses = new ArrayList<>();
    private final List<String[]> feedbackList = new ArrayList<>();
    private boolean gameOver = false;
    private boolean won = false;

    public GameState(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
    }

    public String getSecretWord() { return secretWord; }
    public List<String> getGuesses() { return guesses; }
    public List<String[]> getFeedbackList() { return feedbackList; }
    public boolean isGameOver() { return gameOver; }
    public boolean isWon() { return won; }

    public void addGuess(String guess, String[] feedback) {
        guesses.add(guess.toUpperCase());
        feedbackList.add(feedback);
        if (guess.equalsIgnoreCase(secretWord)) {
            won = true;
            gameOver = true;
        } else if (guesses.size() >= 6) {
            gameOver = true;
        }
    }
}
