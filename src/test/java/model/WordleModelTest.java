package model;

import io.GameLoader;
import io.GameSaver;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

/**
 * WordleModelTest
 * ------------------------------------------------------------
 * Unit tests for the Wordle game's model layer.
 * Focuses on dictionary validation, feedback logic,
 * win/lose detection, reset behavior, and persistence.
 */
public class WordleModelTest {

    /** Test dictionary accepts valid words */
    @Test
    public void testValidWord() {
        Dictionary dict = new Dictionary("assets/words.txt");
        assertTrue("APPLE should be valid", dict.isValidWord("APPLE"));
    }

    /** Test dictionary rejects invalid words */
    @Test
    public void testInvalidWord() {
        Dictionary dict = new Dictionary("assets/words.txt");
        assertFalse("ZZZZZ should not be valid", dict.isValidWord("ZZZZZ"));
    }

    /** Test all-correct guess (all green feedback) */
    @Test
    public void testCorrectGuess() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("HELLO"));
        String[] feedback = model.evaluateGuess("HELLO");
        assertArrayEquals(new String[]{"G","G","G","G","G"}, feedback);
    }

    /** Test partial match (letters in wrong position) */
    @Test
    public void testPartialMatch() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("HELLO"));
        String[] feedback = model.evaluateGuess("LLAMA");
        assertEquals("Y", feedback[0]); // 'L' is in word but wrong spot
    }

    /** Test mostly gray with one yellow ('E' in HELLO) */
    @Test
    public void testIncorrectGuessMostlyGray() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("HELLO"));
        String[] feedback = model.evaluateGuess("TIGER");

        assertEquals("X", feedback[0]); // T
        assertEquals("X", feedback[1]); // I
        assertEquals("X", feedback[2]); // G
        assertEquals("Y", feedback[3]); // E (exists)
        assertEquals("X", feedback[4]); // R
    }

    /** Test win condition triggers properly */
    @Test
    public void testWinCondition() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("APPLE"));
        model.evaluateGuess("APPLE");
        assertTrue(model.getState().isGameOver());
        assertTrue(model.getState().isWon());
    }

    /** Test losing condition after 6 wrong guesses */
    @Test
    public void testLoseAfterSixGuesses() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("APPLE"));
        for (int i = 0; i < 6; i++) {
            model.evaluateGuess("MOUSE");
        }
        assertTrue(model.getState().isGameOver());
        assertFalse(model.getState().isWon());
    }

    /** Test reset clears previous game data */
    @Test
    public void testResetCreatesNewState() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("APPLE"));
        model.evaluateGuess("APPLE");
        model.reset();
        assertFalse(model.getState().isGameOver());
        assertTrue(model.getState().getGuesses().isEmpty());
    }

    /** Test saving and loading the same GameState */
    @Test
    public void testSaveAndLoadGameState() {
        GameState original = new GameState("APPLE");
        original.addGuess("MOUSE", new String[]{"X","X","X","X","X"});
        GameSaver.save(original);

        GameState loaded = GameLoader.load();
        assertNotNull(loaded);
        assertEquals("APPLE", loaded.getSecretWord());
        assertEquals(1, loaded.getGuesses().size());

        // Clean up after test
        new File("assets/save.dat").delete();
    }

    /** Edge Case: Case-insensitive guesses */
    @Test
    public void testCaseInsensitiveGuess() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("APPLE"));
        String[] feedback = model.evaluateGuess("apple"); // lowercase guess
        assertArrayEquals(new String[]{"G","G","G","G","G"}, feedback);
    }

    /** Edge Case: Duplicate letters handled correctly */
    @Test
    public void testDuplicateLetterFeedback() {
        Dictionary dict = new Dictionary("assets/words.txt");
        WordleModel model = new WordleModel(dict);
        model.setState(new GameState("APPLE"));
        String[] feedback = model.evaluateGuess("PAPAL");
        // Expect both 'P's to be recognized at least once
        int countYorG = 0;
        for (String f : feedback) {
            if (f.equals("Y") || f.equals("G")) countYorG++;
        }
        assertTrue("Should have at least two matching letters", countYorG >= 2);
    }
}
