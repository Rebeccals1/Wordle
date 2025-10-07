package control;
import io.GameLoader;
import io.GameSaver;
import model.GameState;
import model.WordleModel;
import view.WordleView;

public class GameController {
    private final WordleModel model;
    private final WordleView view;

    public GameController(WordleModel model, WordleView view) {
        this.model = model;
        this.view = view;
        view.setController(this);
    }

    public void handleGuess(String guess) {
        if (guess.length() != 5) {
            view.showMessage("Word must be 5 letters!");
            return;
        }
        if (!model.isValidWord(guess)) {
            view.showMessage("Not a valid word!");
            return;
        }

        model.evaluateGuess(guess);
        if (model.getState().isGameOver()) {
            if (model.getState().isWon())
                view.showMessage("🎉 You Win!");
            else
                view.showMessage("❌ You Lose! Word was: " + model.getState().getSecretWord());
        }
    }

    public void handleReset() {
        model.reset();
    }

    public void handleSave() {
        GameSaver.save(model.getState());
        view.showMessage("Game saved!");
    }

    public void handleLoad() {
        GameState loaded = GameLoader.load();
        if (loaded != null) {
            model.setState(loaded);
            view.showMessage("Game loaded!");
        } else {
            view.showMessage("No saved game found!");
        }
    }
}
