package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.domain.GetPuzzleUseCase;
import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeViewModel;

public class HomeViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GetPuzzleUseCase mockUseCase;
    private HomeViewModel viewModel;

    @Before
    public void setUp() {
        mockUseCase = mock(GetPuzzleUseCase.class);
        viewModel = new HomeViewModel(mockUseCase);
    }

    @Test
    public void loadPuzzle_success_emitsPuzzle() {
        Puzzle puzzle = new Puzzle("1", "ENCRYPTED", "Hint", "SOLUTION");
        when(mockUseCase.execute()).thenReturn(new PuzzleResult.Success(puzzle));

        viewModel.loadPuzzle();

        PuzzleResult state = viewModel.getPuzzleState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof PuzzleResult.Success);
        assertEquals("ENCRYPTED", ((PuzzleResult.Success) state).puzzle().encryptedText());
    }

    @Test
    public void loadPuzzle_error_emitsError() {
        when(mockUseCase.execute()).thenReturn(new PuzzleResult.Error("Network failure"));

        viewModel.loadPuzzle();

        PuzzleResult state = viewModel.getPuzzleState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof PuzzleResult.Error);
        assertEquals("Network failure", ((PuzzleResult.Error) state).message());
    }
}
