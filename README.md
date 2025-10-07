# Wordle Clone

## Created by
Rebecca L. Smith (Solo Project)
California State Polytechnic University, Pomona
CS 4750 – Software Engineering

---
# Project Overview

This project is a Java Swing recreation of the classic Wordle game, designed to demonstrate mastery of Object-Oriented Programming (OOP), the Model–View–Controller (MVC) pattern, and persistent data storage.

Players have 6 attempts to guess a secret 5-letter word.
Each letter provides feedback:

🟩 **Green**: correct letter, correct position

🟨 **Yellow**: correct letter, wrong position

⬛ **Gray**: letter not in the word

---

## How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/Rebeccals1/Wordle.git

   ```
2. Open the project in IntelliJ IDEA or Eclipse.
3. Make sure you are using JDK 17+.
4. Run Main.java inside the src/main/java directory.
5. The game window (Swing UI) will open automatically.

Note:
All dictionary words are loaded from assets/words.txt, and saved progress is stored in assets/save.dat.

---

## Features Implemented

* Secret Word Selection: Random 5-letter word chosen from a 300+ word dictionary.

* Guess Evaluation: Player receives green, yellow, or gray feedback per letter.

* Dictionary Validation: Only valid English 5-letter words from words.txt are accepted.

* 6 Attempts Limit: Players can make up to six guesses before the game ends.

* Win/Lose Detection: Game ends automatically with appropriate message.

* On-screen Keyboard: Shows letters with color feedback for used keys.

* Persistence (Save/Load): Current game progress saved to a file and reloaded on startup.

* Reset Game: Instantly starts a new round with a new secret word.

* JUnit Tests (Extra Credit): 11 passing model-level tests verifying core logic, state handling, and persistence.

---

## Controls
**Action**	            **Control**
Enter Guess	        Type word in input box and click Guess
Reset Game	        Click Reset
Save Game	        Click Save
Load Game	        Click Load

---
# Known Issues

* Keyboard Animation: Letters update after a guess but do not animate flips (aesthetic only).

* Limited Dictionary: Words are limited to the provided assets/words.txt; external sources not supported.

---

# Architecture

The project follows the MVC pattern:

**Model**: Game logic and state (WordleModel, GameState, Dictionary)

**View**: Swing-based UI (WordleView, GridPanel, KeyboardPanel)

**Controller**: Handles user actions (GameController)

**Persistence**: File I/O (GameSaver, GameLoader)

**Utility**: Observer pattern support (ObservableModel, ModelListener)

This ensures a clean separation of logic, presentation, and interaction layers.

---

Object-Oriented Principles

- Encapsulation: All state fields are private, accessed via getters/setters.

- Inheritance: Observable model class allows view updates via listeners.

- Polymorphism: Reusable MVC listener interfaces.

- Abstraction: UI separated from logic — model independent of Swing.

---

# Testing

Implemented JUnit 4.13 tests for all core model behaviors.

**Tests include:**

* Word validation (valid/invalid words)

* Feedback correctness (G/Y/X)

* Win/loss logic

* Reset behavior

* Save/load persistence

* Case-insensitive input

* Duplicate letter handling

All 11 tests pass successfully.

---

# Known Issues

- Keyboard feedback updates only after submitting a guess (no animation).

- Limited dictionary — only words from assets/words.txt are accepted.

- No word wrap or resizing support for smaller screens.

---

# External Libraries

* JUnit 4.13.2 — for automated testing

* Hamcrest 1.3 — used internally by JUnit for assertions

(No other external libraries used — Swing is part of standard Java.)

---

# File Structure

```
Wordle/
├── src/
│   ├── main/java/
│   │   ├── control/         # Controller layer
│   │   ├── io/              # Save/load persistence
│   │   ├── model/           # Game logic + data structures
│   │   ├── util/            # Observer pattern helpers
│   │   └── view/            # Swing UI components
│   └── test/java/
│       └── model/           # JUnit tests
│           └── WordleModelTest.java
├── assets/
│   ├── words.txt            # Dictionary
│   └── save.dat             # Generated save file
├── README.md
└── REPORT.md

```
