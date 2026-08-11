package br.com.lucaslima.cryptogram.feature.game.domain;

public final class GameLetter {

    private final int listIndex;
    private final char cipherChar;
    private final char solutionChar;
    private final boolean revealed;
    private final Character guess;
    private final boolean correct;
    private final boolean wrong;

    public GameLetter(int listIndex, char cipherChar, char solutionChar,
                      boolean revealed, Character guess, boolean correct, boolean wrong) {
        this.listIndex = listIndex;
        this.cipherChar = cipherChar;
        this.solutionChar = solutionChar;
        this.revealed = revealed;
        this.guess = guess;
        this.correct = correct;
        this.wrong = wrong;
    }

    public LetterState computeState(int selectedIndex) {
        if (revealed) {
            return LetterState.REVEALED;
        }
        if (correct) {
            return LetterState.CORRECT;
        }
        if (wrong) {
            return LetterState.WRONG;
        }
        if (listIndex == selectedIndex) {
            return LetterState.SELECTED;
        }
        if (guess != null) {
            return LetterState.FILLED;
        }
        return LetterState.EMPTY;
    }

    public GameLetter withGuess(char newGuess) {
        return new GameLetter(listIndex, cipherChar, solutionChar, revealed, newGuess, false, false);
    }

    public GameLetter cleared() {
        return new GameLetter(listIndex, cipherChar, solutionChar, revealed, null, false, false);
    }

    public GameLetter withCorrect() {
        return new GameLetter(listIndex, cipherChar, solutionChar, revealed, guess, true, false);
    }

    public GameLetter withWrong() {
        return new GameLetter(listIndex, cipherChar, solutionChar, revealed, guess, false, true);
    }

    public boolean validateGuess() {
        if (guess == null) {
            return false;
        }
        return Character.toUpperCase(guess) == Character.toUpperCase(solutionChar);
    }

    public int getListIndex() {
        return listIndex;
    }

    public char getCipherChar() {
        return cipherChar;
    }

    public Character getGuess() {
        return guess;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isWrong() {
        return wrong;
    }
}
