Credits

Created by Rebecca L. Smith
for CS 4750: Software Engineering
California State Polytechnic University, Pomona
Fall 2025

---

# **REPORT.md**

```markdown
# Project Report — Wordle Clone

---

## Design Decisions

### Architecture (MVC)
This project follows a clear **Model-View-Controller (MVC)** architecture:

- **Model:** Contains all core logic and state tracking (e.g., `WordleModel`, `GameState`, `Dictionary`).  
  - Handles word validation, guess evaluation, win/loss detection, and save/load functionality.  
  - Has no dependencies on Swing or UI components.

- **View:** Handles all UI rendering through Swing components (`WordleView`, `GridPanel`, `KeyboardPanel`).  
  - Displays the 6x5 letter grid, colored feedback, and on-screen keyboard.  
  - Subscribes to the model via the **Observer pattern** (`ObservableModel` + `ModelListener`).

- **Controller:** (`GameController`) connects user input to model updates.  
  - Handles guessing, resetting, and saving/loading actions.  
  - Keeps business logic out of the view layer.

**Why Swing?**  
Swing was chosen for simplicity and reliability. It allowed direct control over layout, drawing, and event handling without module-path setup. The project doesn’t require animations or 3D rendering, so JavaFX wasn’t necessary.

---

### Data Structures
- **`GameState`:** Stores the secret word, guess history, and feedback for each attempt.  
  - Uses `List<String>` for guesses and `List<String[]>` for per-letter feedback.
- **`Dictionary`:** Loads valid 5-letter words into a `List<String>` for validation.
- **`ObservableModel`:** Maintains a list of listeners (observers) using `List<ModelListener>` to notify views when data changes.

**Why these choices?**
- Lists provide O(1) indexing and easy iteration per guess.
- Simplicity and readability align with the project’s small data scope (max 6 guesses × 5 letters).
- Serializable classes make it easy to persist state with minimal overhead.

---

### Algorithms
- **Guess Evaluation:**  
  Each letter of the guess is compared to the secret word:  
  - Exact match → Green (G)  
  - Exists elsewhere → Yellow (Y)  
  - Not present → Gray (X)  
  This is an O(n) algorithm where n = 5 (constant-time per guess).

- **Dictionary Validation:**  
  Uses `List.contains()` → O(n) where n = number of words (≈ 300–400), which is negligible.

- **Persistence (Save/Load):**  
  Uses Java’s built-in `ObjectOutputStream` and `ObjectInputStream` to serialize and restore the `GameState` object from `assets/save.dat`.

---

## Challenges Faced

### Challenge 1: Save/Load File Not Found
**Problem:**  
Early versions couldn’t locate `save.dat` when loading after restart.  

**Solution:**  
Used `System.getProperty("user.dir")` to dynamically locate the project’s root directory and ensure consistent paths across systems.

---

### Challenge 2: Serialization Error (`NotSerializableException`)
**Problem:**  
Initial save/load attempts failed because `GameState` didn’t implement `Serializable`.  

**Solution:**  
Updated `GameState` to implement `Serializable` and added `serialVersionUID` for version compatibility.

---

### Challenge 3: UI Layout and Centering
**Problem:**  
The grid and keyboard were misaligned, and colors weren’t rendering correctly.  

**Solution:**  
Refactored `GridPanel` with centered coordinates, adjusted spacing, and ensured all Swing components use `BorderLayout` with proper padding.

---

## What We Learned
- **Encapsulation:** Keeping model fields private and accessed only through methods improves control and safety.  
- **Abstraction:** The MVC pattern enforces separation between logic and presentation.  
- **Observer Pattern:** Allowed real-time UI updates without direct coupling between components.  
- **Testing:** Learned how to write focused JUnit tests for model logic, covering normal and edge cases.

---

## If We Had More Time
- Add **tile flip animations** using JavaFX or Swing Timer transitions.  
- Implement **statistics tracking** (games played, win percentage, average guesses).  
- Add **hard mode** (must use revealed hints).  
- Improve **UI polish** with a dark mode toggle and keyboard animation.

---

## Testing Summary
We implemented **11 JUnit 4 tests** covering:
- Word validation  
- Feedback correctness (G/Y/X)  
- Win/lose logic  
- Reset state  
- Save/load persistence  
- Case-insensitivity and duplicate-letter edge cases  

All tests pass successfully, confirming robustness of the model logic and persistence layer.
