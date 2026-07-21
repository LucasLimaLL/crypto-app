package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

public class PuzzleRepositoryImplTest {

    private final PuzzleRepositoryImpl repository = new PuzzleRepositoryImpl();

    @Test
    public void getNextPuzzle_returnsNonNull() {
        assertNotNull(repository.getNextPuzzle());
    }

    @Test
    public void getNextPuzzle_hasExpectedId() {
        assertEquals("puzzle-001", repository.getNextPuzzle().id());
    }

    @Test
    public void getNextPuzzle_hasNonEmptyEncryptedText() {
        assertNotNull(repository.getNextPuzzle().encryptedText());
    }

    @Test
    public void getNextPuzzle_hasNonEmptyHint() {
        assertNotNull(repository.getNextPuzzle().hint());
    }

    @Test
    public void getNextPuzzle_hasNonEmptySolution() {
        assertNotNull(repository.getNextPuzzle().solution());
    }

    @Test
    public void getNextPuzzle_calledTwice_returnsSamePuzzle() {
        Puzzle first = repository.getNextPuzzle();
        Puzzle second = repository.getNextPuzzle();
        assertEquals(first, second);
    }
}
