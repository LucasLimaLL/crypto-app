package br.com.lucaslima.cryptogram.feature.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.GamePuzzle;
import br.com.lucaslima.cryptogram.feature.game.domain.GameUiState;
import br.com.lucaslima.cryptogram.feature.game.domain.LetterState;
import br.com.lucaslima.cryptogram.feature.game.ui.GameViewModel;

public class GameViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GameViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new GameViewModel(new GamePuzzle("ABCD", "VAMO"));
    }

    @Test
    public void initialState_emitsUiState() {
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
    }

    @Test
    public void initialState_hasNoSelection() {
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals(GameViewModel.NO_SELECTION, state.getSelectedIndex());
    }

    @Test
    public void initialState_allLettersEmpty() {
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        for (GameLetter letter : state.getLetters()) {
            assertNull(letter.getGuess());
        }
    }

    @Test
    public void initialState_revealedLettersHaveGuess() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO", 'A'));
        GameUiState state = vm.getUiState().getValue();
        assertNotNull(state);
        GameLetter first = state.getLetters().get(0);
        assertTrue(first.isRevealed());
        assertEquals('V', (char) first.getGuess());
    }

    @Test
    public void initialState_skipsSpacesInLettersList() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("AB CD", "VA MO"));
        GameUiState state = vm.getUiState().getValue();
        assertNotNull(state);
        assertEquals(4, state.getLetters().size());
    }

    @Test
    public void selectLetter_setsSelectedIndex() {
        viewModel.selectLetter(1);
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals(1, state.getSelectedIndex());
    }

    @Test
    public void selectLetter_withNegativeIndex_doesNothing() {
        viewModel.selectLetter(-1);
        assertEquals(GameViewModel.NO_SELECTION, viewModel.getUiState().getValue().getSelectedIndex());
    }

    @Test
    public void selectLetter_withOutOfBoundsIndex_doesNothing() {
        viewModel.selectLetter(100);
        assertEquals(GameViewModel.NO_SELECTION, viewModel.getUiState().getValue().getSelectedIndex());
    }

    @Test
    public void selectLetter_onRevealedLetter_doesNothing() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO", 'A'));
        vm.selectLetter(0);
        assertEquals(GameViewModel.NO_SELECTION, vm.getUiState().getValue().getSelectedIndex());
    }

    @Test
    public void selectLetter_onCorrectLetter_doesNothing() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('V');
        viewModel.validatePuzzle();
        viewModel.selectLetter(0);
        assertNotEquals(0, viewModel.getUiState().getValue().getSelectedIndex());
    }

    @Test
    public void onKeyPressed_whenNoSelection_doesNothing() {
        viewModel.onKeyPressed('X');
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        for (GameLetter l : state.getLetters()) {
            assertNull(l.getGuess());
        }
    }

    @Test
    public void onKeyPressed_fillsSelectedLetter() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('X');
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals('X', (char) state.getLetters().get(0).getGuess());
    }

    @Test
    public void onKeyPressed_propagatesToAllLettersWithSameCipherChar() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABAC", "VAVA"));
        vm.selectLetter(0);
        vm.onKeyPressed('V');
        GameUiState state = vm.getUiState().getValue();
        assertNotNull(state);
        assertEquals('V', (char) state.getLetters().get(0).getGuess());
        assertEquals('V', (char) state.getLetters().get(2).getGuess());
        assertNull(state.getLetters().get(1).getGuess());
    }

    @Test
    public void onKeyPressed_advancesCursorToNextAvailableLetter() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('X');
        assertEquals(1, viewModel.getUiState().getValue().getSelectedIndex());
    }

    @Test
    public void onKeyPressed_wrapsAroundWhenReachingEnd() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO"));
        vm.selectLetter(3);
        vm.onKeyPressed('X');
        int selected = vm.getUiState().getValue().getSelectedIndex();
        assertTrue(selected < 3);
    }

    @Test
    public void onKeyPressed_onRevealedLetter_doesNothing() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO", 'A'));
        vm.selectLetter(1);
        vm.onKeyPressed('X');
        vm.selectLetter(0);
        vm.onKeyPressed('Z');
        assertEquals('V', (char) vm.getUiState().getValue().getLetters().get(0).getGuess());
    }

    @Test
    public void onDelete_clearsGuessForSelectedCipherChar() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('X');
        viewModel.selectLetter(0);
        viewModel.onDelete();
        assertNull(viewModel.getUiState().getValue().getLetters().get(0).getGuess());
    }

    @Test
    public void onDelete_propagatesToAllLettersWithSameCipherChar() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABAC", "VAVA"));
        vm.selectLetter(0);
        vm.onKeyPressed('V');
        vm.selectLetter(0);
        vm.onDelete();
        GameUiState state = vm.getUiState().getValue();
        assertNotNull(state);
        assertNull(state.getLetters().get(0).getGuess());
        assertNull(state.getLetters().get(2).getGuess());
    }

    @Test
    public void onDelete_whenNoSelection_doesNothing() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('X');
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO"));
        vm.onDelete();
        for (GameLetter l : vm.getUiState().getValue().getLetters()) {
            assertNull(l.getGuess());
        }
    }

    @Test
    public void onDelete_onRevealedLetter_doesNothing() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO", 'A'));
        vm.selectLetter(1);
        vm.selectLetter(0);
        vm.onDelete();
        assertEquals('V', (char) vm.getUiState().getValue().getLetters().get(0).getGuess());
    }

    @Test
    public void validatePuzzle_marksCorrectGuessAsCorrect() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('V');
        viewModel.validatePuzzle();
        assertTrue(viewModel.getUiState().getValue().getLetters().get(0).isCorrect());
    }

    @Test
    public void validatePuzzle_marksWrongGuessAsWrong() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('X');
        viewModel.validatePuzzle();
        assertTrue(viewModel.getUiState().getValue().getLetters().get(0).isWrong());
    }

    @Test
    public void validatePuzzle_skipsRevealedLetters() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("ABCD", "VAMO", 'A'));
        vm.validatePuzzle();
        assertFalse(vm.getUiState().getValue().getLetters().get(0).isCorrect());
        assertTrue(vm.getUiState().getValue().getLetters().get(0).isRevealed());
    }

    @Test
    public void validatePuzzle_skipsLettersWithNullGuess() {
        viewModel.validatePuzzle();
        for (GameLetter l : viewModel.getUiState().getValue().getLetters()) {
            assertFalse(l.isCorrect());
            assertFalse(l.isWrong());
        }
    }

    @Test
    public void isComplete_whenAllLettersCorrectOrRevealed_returnsTrue() {
        GameViewModel vm = new GameViewModel(new GamePuzzle("AB", "VA", 'A'));
        vm.selectLetter(1);
        vm.onKeyPressed('A');
        vm.validatePuzzle();
        assertTrue(vm.getUiState().getValue().isComplete());
    }

    @Test
    public void isComplete_whenSomeLettersMissing_returnsFalse() {
        viewModel.selectLetter(0);
        viewModel.onKeyPressed('V');
        viewModel.validatePuzzle();
        assertFalse(viewModel.getUiState().getValue().isComplete());
    }

    @Test
    public void letterState_reflectsSelectedIndex() {
        viewModel.selectLetter(0);
        GameUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals(LetterState.SELECTED,
                state.getLetters().get(0).computeState(state.getSelectedIndex()));
    }

    private static void assertNotEquals(int unexpected, int actual) {
        assertFalse("Expected " + unexpected + " to differ from " + actual,
                unexpected == actual);
    }
}
