package model;
import util.ObservableModel;

public class WordleModel extends ObservableModel {
    private GameState state;
    private final Dictionary dictionary;

    public WordleModel(Dictionary dict) {
        this.dictionary = dict;
        this.state = new GameState(dict.getRandomWord());
    }

    public GameState getState() { return state; }

    public void setState(GameState s) {
        this.state = s;
        notifyListeners();
    }

    public String[] evaluateGuess(String guess) {
        guess = guess.toUpperCase();
        String secret = state.getSecretWord();
        String[] feedback = new String[5];

        for (int i = 0; i < 5; i++) {
            char c = guess.charAt(i);
            if (c == secret.charAt(i)) feedback[i] = "G";
            else if (secret.contains(String.valueOf(c))) feedback[i] = "Y";
            else feedback[i] = "X";
        }

        state.addGuess(guess, feedback);
        notifyListeners();
        return feedback;
    }

    public boolean isValidWord(String word) {
        return dictionary.isValidWord(word.toUpperCase());
    }

    public void reset() {
        this.state = new GameState(dictionary.getRandomWord());
        notifyListeners();
    }
}
