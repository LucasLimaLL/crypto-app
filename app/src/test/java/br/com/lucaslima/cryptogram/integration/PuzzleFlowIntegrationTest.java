package br.com.lucaslima.cryptogram.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.home.domain.GetPuzzleUseCase;
import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;

public class PuzzleFlowIntegrationTest {

    private final GetPuzzleUseCase useCase = new GetPuzzleUseCase(new PuzzleRepositoryImpl());

    @Test
    public void execute_returnsSuccess() {
        assertTrue(useCase.execute() instanceof PuzzleResult.Success);
    }

    @Test
    public void execute_puzzleHasExpectedId() {
        PuzzleResult.Success result = (PuzzleResult.Success) useCase.execute();
        assertEquals("puzzle-001", result.puzzle().id());
    }

    @Test
    public void execute_puzzleHasNonEmptyEncryptedText() {
        PuzzleResult.Success result = (PuzzleResult.Success) useCase.execute();
        assertNotNull(result.puzzle().encryptedText());
        assertFalse(result.puzzle().encryptedText().isEmpty());
    }

    @Test
    public void execute_puzzleHasNonEmptyHint() {
        PuzzleResult.Success result = (PuzzleResult.Success) useCase.execute();
        assertNotNull(result.puzzle().hint());
        assertFalse(result.puzzle().hint().isEmpty());
    }

    @Test
    public void execute_puzzleHasNonEmptySolution() {
        PuzzleResult.Success result = (PuzzleResult.Success) useCase.execute();
        assertNotNull(result.puzzle().solution());
        assertFalse(result.puzzle().solution().isEmpty());
    }

    @Test
    public void execute_calledTwice_returnsSamePuzzle() {
        Puzzle first = ((PuzzleResult.Success) useCase.execute()).puzzle();
        Puzzle second = ((PuzzleResult.Success) useCase.execute()).puzzle();
        assertEquals(first, second);
    }
}
