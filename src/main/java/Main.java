import control.GameController;
import io.GameLoader;
import model.Dictionary;
import model.GameState;
import model.WordleModel;
import view.WordleView;

public class Main {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);

        // 🔹 Auto-load previous game if save file exists
        GameState saved = GameLoader.load();
        if (saved != null) {
            model.setState(saved);
            System.out.println("Loaded previous game automatically.");
        }

        WordleView view = new WordleView(model);
        new GameController(model, view);
    }
}
