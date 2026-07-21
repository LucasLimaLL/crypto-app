package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepository;
import br.com.lucaslima.cryptogram.feature.home.domain.GetPuzzleUseCase;
import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;

public class GetPuzzleUseCaseTest {

    private PuzzleRepository mockRepository;
    private GetPuzzleUseCase useCase;

    @Before
    public void setUp() {
        mockRepository = mock(PuzzleRepository.class);
        useCase = new GetPuzzleUseCase(mockRepository);
    }

    @Test
    public void execute_repositoryReturnsPuzzle_returnsSuccess() {
        Puzzle puzzle = new Puzzle("p1", "ENC", "hint", "SOL");
        when(mockRepository.getNextPuzzle()).thenReturn(puzzle);
        PuzzleResult result = useCase.execute();
        assertTrue(result instanceof PuzzleResult.Success);
        assertEquals(puzzle, ((PuzzleResult.Success) result).puzzle());
    }

    @Test
    public void execute_repositoryReturnsNull_returnsError() {
        when(mockRepository.getNextPuzzle()).thenReturn(null);
        PuzzleResult result = useCase.execute();
        assertTrue(result instanceof PuzzleResult.Error);
        assertEquals("No puzzles available at this time.", ((PuzzleResult.Error) result).message());
    }

    @Test
    public void execute_repositoryThrowsWithMessage_returnsError() {
        when(mockRepository.getNextPuzzle()).thenThrow(new RuntimeException("DB error"));
        PuzzleResult result = useCase.execute();
        assertTrue(result instanceof PuzzleResult.Error);
        assertEquals("DB error", ((PuzzleResult.Error) result).message());
    }

    @Test
    public void execute_repositoryThrowsWithNullMessage_returnsUnknownError() {
        when(mockRepository.getNextPuzzle()).thenThrow(new RuntimeException((String) null));
        PuzzleResult result = useCase.execute();
        assertTrue(result instanceof PuzzleResult.Error);
        assertEquals("Unknown error", ((PuzzleResult.Error) result).message());
    }

    @Test
    public void execute_calledTwice_returnsIndependentResults() {
        Puzzle puzzle = new Puzzle("p1", "ENC", "hint", "SOL");
        when(mockRepository.getNextPuzzle()).thenReturn(puzzle);
        PuzzleResult first = useCase.execute();
        PuzzleResult second = useCase.execute();
        assertTrue(first instanceof PuzzleResult.Success);
        assertTrue(second instanceof PuzzleResult.Success);
    }
}
