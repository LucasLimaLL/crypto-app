package br.com.lucaslima.cryptogram.feature.game.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameUiState {

    private final List<GameLetter> letters;
    private final int selectedIndex;
    private final boolean complete;

    public GameUiState(List<GameLetter> letters, int selectedIndex) {
        this.letters = Collections.unmodifiableList(new ArrayList<>(letters));
        this.selectedIndex = selectedIndex;
        this.complete = letters.stream().allMatch(l -> l.isCorrect() || l.isRevealed());
    }

    public List<GameLetter> getLetters() {
        return letters;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public boolean isComplete() {
        return complete;
    }
}
