package br.com.lucaslima.cryptogram.feature.game.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.GamePuzzle;
import br.com.lucaslima.cryptogram.feature.game.domain.GameUiState;

public class GameViewModel extends ViewModel {

    public static final int NO_SELECTION = -1;

    private final GamePuzzle puzzle;
    private final List<GameLetter> letters = new ArrayList<>();
    private int selectedIndex = NO_SELECTION;
    private final MutableLiveData<GameUiState> uiState = new MutableLiveData<>();

    public GameViewModel() {
        this(new GamePuzzle("DNFZK GZQNP", "VAMOS JOGAR", 'D', 'N', 'K'));
    }

    public GameViewModel(GamePuzzle puzzle) {
        this.puzzle = puzzle;
        initLetters();
        emitState();
    }

    private void initLetters() {
        int listIndex = 0;
        for (int i = 0; i < puzzle.length(); i++) {
            if (!puzzle.isSpace(i)) {
                char cipherChar = puzzle.cipherAt(i);
                char solutionChar = puzzle.solutionAt(i);
                boolean revealed = puzzle.isRevealedCipher(cipherChar);
                Character initialGuess = revealed ? solutionChar : null;
                letters.add(new GameLetter(listIndex, cipherChar, solutionChar,
                        revealed, initialGuess, false, false));
                listIndex++;
            }
        }
    }

    public void selectLetter(int index) {
        if (index < 0 || index >= letters.size()) {
            return;
        }
        GameLetter letter = letters.get(index);
        if (letter.isRevealed() || letter.isCorrect()) {
            return;
        }
        selectedIndex = index;
        emitState();
    }

    public void onKeyPressed(char key) {
        if (selectedIndex == NO_SELECTION) {
            return;
        }
        GameLetter selected = letters.get(selectedIndex);
        if (selected.isRevealed() || selected.isCorrect()) {
            return;
        }
        char cipherChar = selected.getCipherChar();
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).getCipherChar() == cipherChar) {
                letters.set(i, letters.get(i).withGuess(key));
            }
        }
        advanceCursor();
        emitState();
    }

    public void onDelete() {
        if (selectedIndex == NO_SELECTION) {
            return;
        }
        GameLetter selected = letters.get(selectedIndex);
        if (selected.isRevealed() || selected.isCorrect()) {
            return;
        }
        char cipherChar = selected.getCipherChar();
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).getCipherChar() == cipherChar) {
                letters.set(i, letters.get(i).cleared());
            }
        }
        emitState();
    }

    public void validatePuzzle() {
        for (int i = 0; i < letters.size(); i++) {
            GameLetter letter = letters.get(i);
            if (letter.isRevealed() || letter.isCorrect() || letter.getGuess() == null) {
                continue;
            }
            if (letter.validateGuess()) {
                letters.set(i, letter.withCorrect());
            } else {
                letters.set(i, letter.withWrong());
            }
        }
        emitState();
    }

    private void advanceCursor() {
        for (int i = selectedIndex + 1; i < letters.size(); i++) {
            if (!letters.get(i).isRevealed() && !letters.get(i).isCorrect()) {
                selectedIndex = i;
                return;
            }
        }
        for (int i = 0; i < selectedIndex; i++) {
            if (!letters.get(i).isRevealed() && !letters.get(i).isCorrect()) {
                selectedIndex = i;
                return;
            }
        }
    }

    private void emitState() {
        uiState.setValue(new GameUiState(new ArrayList<>(letters), selectedIndex));
    }

    public LiveData<GameUiState> getUiState() {
        return uiState;
    }
}
