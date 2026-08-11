package br.com.lucaslima.cryptogram.feature.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.game.domain.GamePuzzle;

public class GamePuzzleTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructor_whenCipherIsNull_throwsException() {
        new GamePuzzle(null, "VAMOS");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_whenSolutionIsNull_throwsException() {
        new GamePuzzle("DNFZK", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_whenLengthsMismatch_throwsException() {
        new GamePuzzle("DNFZK", "VAM");
    }

    @Test
    public void constructor_normalizesToUpperCase() {
        GamePuzzle puzzle = new GamePuzzle("dnfzk", "vamos");
        assertEquals('D', puzzle.cipherAt(0));
        assertEquals('V', puzzle.solutionAt(0));
    }

    @Test
    public void length_returnsFullLengthIncludingSpaces() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK GZQNP", "VAMOS JOGAR");
        assertEquals(11, puzzle.length());
    }

    @Test
    public void cipherAt_returnsCorrectChar() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS");
        assertEquals('N', puzzle.cipherAt(1));
    }

    @Test
    public void solutionAt_returnsCorrectChar() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS");
        assertEquals('A', puzzle.solutionAt(1));
    }

    @Test
    public void isSpace_whenSpaceChar_returnsTrue() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK GZQNP", "VAMOS JOGAR");
        assertTrue(puzzle.isSpace(5));
    }

    @Test
    public void isSpace_whenNonSpaceChar_returnsFalse() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK GZQNP", "VAMOS JOGAR");
        assertFalse(puzzle.isSpace(0));
    }

    @Test
    public void isRevealedCipher_whenCharIsRevealed_returnsTrue() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS", 'D', 'N');
        assertTrue(puzzle.isRevealedCipher('D'));
        assertTrue(puzzle.isRevealedCipher('N'));
    }

    @Test
    public void isRevealedCipher_whenCharIsNotRevealed_returnsFalse() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS", 'D');
        assertFalse(puzzle.isRevealedCipher('N'));
    }

    @Test
    public void isRevealedCipher_isCaseInsensitive() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS", 'D');
        assertTrue(puzzle.isRevealedCipher('d'));
    }

    @Test
    public void constructor_withNoRevealedChars_noneAreRevealed() {
        GamePuzzle puzzle = new GamePuzzle("DNFZK", "VAMOS");
        assertFalse(puzzle.isRevealedCipher('D'));
        assertFalse(puzzle.isRevealedCipher('N'));
    }
}
