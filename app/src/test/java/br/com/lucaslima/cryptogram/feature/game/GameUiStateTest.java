package br.com.lucaslima.cryptogram.feature.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.GameUiState;

public class GameUiStateTest {

    @Test
    public void getLetters_returnsAllLetters() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, null, false, false);
        GameLetter b = new GameLetter(1, 'N', 'A', false, null, false, false);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertEquals(2, state.getLetters().size());
    }

    @Test
    public void getLetters_returnsUnmodifiableList() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, null, false, false);
        GameUiState state = new GameUiState(Collections.singletonList(a), -1);
        boolean threw = false;
        try {
            state.getLetters().add(a);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void getSelectedIndex_returnsCorrectIndex() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, null, false, false);
        GameUiState state = new GameUiState(Collections.singletonList(a), 2);
        assertEquals(2, state.getSelectedIndex());
    }

    @Test
    public void isComplete_whenAllCorrect_returnsTrue() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, 'V', true, false);
        GameLetter b = new GameLetter(1, 'N', 'A', false, 'A', true, false);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertTrue(state.isComplete());
    }

    @Test
    public void isComplete_whenAllRevealed_returnsTrue() {
        GameLetter a = new GameLetter(0, 'D', 'V', true, 'V', false, false);
        GameLetter b = new GameLetter(1, 'N', 'A', true, 'A', false, false);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertTrue(state.isComplete());
    }

    @Test
    public void isComplete_whenMixedCorrectAndRevealed_returnsTrue() {
        GameLetter a = new GameLetter(0, 'D', 'V', true, 'V', false, false);
        GameLetter b = new GameLetter(1, 'N', 'A', false, 'A', true, false);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertTrue(state.isComplete());
    }

    @Test
    public void isComplete_whenSomeEmpty_returnsFalse() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, 'V', true, false);
        GameLetter b = new GameLetter(1, 'N', 'A', false, null, false, false);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertFalse(state.isComplete());
    }

    @Test
    public void isComplete_whenSomeWrong_returnsFalse() {
        GameLetter a = new GameLetter(0, 'D', 'V', false, 'V', true, false);
        GameLetter b = new GameLetter(1, 'N', 'A', false, 'X', false, true);
        GameUiState state = new GameUiState(Arrays.asList(a, b), -1);
        assertFalse(state.isComplete());
    }

    @Test
    public void isComplete_whenEmptyList_returnsTrue() {
        GameUiState state = new GameUiState(Collections.emptyList(), -1);
        assertTrue(state.isComplete());
    }
}
