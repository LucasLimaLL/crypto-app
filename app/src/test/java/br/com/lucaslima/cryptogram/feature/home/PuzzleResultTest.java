package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;

public class PuzzleResultTest {

    private static final Puzzle SAMPLE = new Puzzle("id", "enc", "hint", "sol");

    @Test
    public void loading_isCorrectType() {
        assertTrue(new PuzzleResult.Loading() instanceof PuzzleResult.Loading);
    }

    @Test
    public void loading_equality() {
        assertEquals(new PuzzleResult.Loading(), new PuzzleResult.Loading());
    }

    @Test
    public void success_holdsPuzzle() {
        PuzzleResult.Success result = new PuzzleResult.Success(SAMPLE);
        assertEquals(SAMPLE, result.puzzle());
    }

    @Test
    public void success_equality_samePuzzle() {
        assertEquals(new PuzzleResult.Success(SAMPLE), new PuzzleResult.Success(SAMPLE));
    }

    @Test
    public void success_inequality_differentPuzzle() {
        Puzzle other = new Puzzle("other-id", "enc", "hint", "sol");
        assertNotEquals(new PuzzleResult.Success(SAMPLE), new PuzzleResult.Success(other));
    }

    @Test
    public void error_holdsMessage() {
        PuzzleResult.Error result = new PuzzleResult.Error("not found");
        assertEquals("not found", result.message());
    }

    @Test
    public void error_equality_sameMessage() {
        assertEquals(new PuzzleResult.Error("e"), new PuzzleResult.Error("e"));
    }

    @Test
    public void error_inequality_differentMessage() {
        assertNotEquals(new PuzzleResult.Error("a"), new PuzzleResult.Error("b"));
    }

    @Test
    public void subtypes_areDistinctFromEachOther() {
        assertNotEquals(new PuzzleResult.Loading(), new PuzzleResult.Success(SAMPLE));
        assertNotEquals(new PuzzleResult.Loading(), new PuzzleResult.Error("e"));
        assertNotEquals(new PuzzleResult.Success(SAMPLE), new PuzzleResult.Error("e"));
    }
}
