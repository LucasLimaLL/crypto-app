package br.com.lucaslima.cryptogram.feature.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.LetterState;

public class GameLetterTest {

    private static final int NO_SELECTION = -1;

    @Test
    public void computeState_whenRevealed_returnsRevealed() {
        GameLetter letter = new GameLetter(0, 'D', 'V', true, 'V', false, false);
        assertEquals(LetterState.REVEALED, letter.computeState(NO_SELECTION));
    }

    @Test
    public void computeState_whenRevealed_ignoresSelection() {
        GameLetter letter = new GameLetter(0, 'D', 'V', true, 'V', false, false);
        assertEquals(LetterState.REVEALED, letter.computeState(0));
    }

    @Test
    public void computeState_whenCorrect_returnsCorrect() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'V', true, false);
        assertEquals(LetterState.CORRECT, letter.computeState(NO_SELECTION));
    }

    @Test
    public void computeState_whenWrong_returnsWrong() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, true);
        assertEquals(LetterState.WRONG, letter.computeState(NO_SELECTION));
    }

    @Test
    public void computeState_whenSelected_returnsSelected() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, null, false, false);
        assertEquals(LetterState.SELECTED, letter.computeState(0));
    }

    @Test
    public void computeState_whenFilledAndSelected_returnsSelected() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, false);
        assertEquals(LetterState.SELECTED, letter.computeState(0));
    }

    @Test
    public void computeState_whenFilled_returnsFilled() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, false);
        assertEquals(LetterState.FILLED, letter.computeState(1));
    }

    @Test
    public void computeState_whenEmpty_returnsEmpty() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, null, false, false);
        assertEquals(LetterState.EMPTY, letter.computeState(1));
    }

    @Test
    public void withGuess_returnsNewLetterWithGuessAndResetState() {
        GameLetter original = new GameLetter(0, 'D', 'V', false, null, false, false);
        GameLetter withGuess = original.withGuess('X');

        assertEquals('X', (char) withGuess.getGuess());
        assertFalse(withGuess.isCorrect());
        assertFalse(withGuess.isWrong());
    }

    @Test
    public void withGuess_onWrongLetter_resetsWrongState() {
        GameLetter wrong = new GameLetter(0, 'D', 'V', false, 'X', false, true);
        GameLetter reguessed = wrong.withGuess('Y');

        assertFalse(reguessed.isWrong());
        assertEquals('Y', (char) reguessed.getGuess());
    }

    @Test
    public void cleared_returnsLetterWithNullGuessAndNoState() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, true);
        GameLetter cleared = letter.cleared();

        assertNull(cleared.getGuess());
        assertFalse(cleared.isCorrect());
        assertFalse(cleared.isWrong());
    }

    @Test
    public void withCorrect_setsCorrectFlag() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'V', false, false);
        GameLetter correct = letter.withCorrect();

        assertTrue(correct.isCorrect());
        assertFalse(correct.isWrong());
    }

    @Test
    public void withWrong_setsWrongFlag() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, false);
        GameLetter wrong = letter.withWrong();

        assertTrue(wrong.isWrong());
        assertFalse(wrong.isCorrect());
    }

    @Test
    public void validateGuess_whenNullGuess_returnsFalse() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, null, false, false);
        assertFalse(letter.validateGuess());
    }

    @Test
    public void validateGuess_whenCorrectGuess_returnsTrue() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'V', false, false);
        assertTrue(letter.validateGuess());
    }

    @Test
    public void validateGuess_whenCorrectGuessDifferentCase_returnsTrue() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'v', false, false);
        assertTrue(letter.validateGuess());
    }

    @Test
    public void validateGuess_whenWrongGuess_returnsFalse() {
        GameLetter letter = new GameLetter(0, 'D', 'V', false, 'X', false, false);
        assertFalse(letter.validateGuess());
    }

    @Test
    public void getters_returnCorrectValues() {
        GameLetter letter = new GameLetter(3, 'Z', 'O', false, 'A', false, false);

        assertEquals(3, letter.getListIndex());
        assertEquals('Z', letter.getCipherChar());
        assertEquals('A', (char) letter.getGuess());
        assertFalse(letter.isRevealed());
        assertFalse(letter.isCorrect());
        assertFalse(letter.isWrong());
    }
}
