package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

public class PuzzleTest {

    @Test
    public void puzzle_holdsId() {
        Puzzle puzzle = new Puzzle("id-1", "ENC", "hint", "SOL");
        assertEquals("id-1", puzzle.id());
    }

    @Test
    public void puzzle_holdsEncryptedText() {
        Puzzle puzzle = new Puzzle("id-1", "ENCRYPTED", "hint", "SOL");
        assertEquals("ENCRYPTED", puzzle.encryptedText());
    }

    @Test
    public void puzzle_holdsHint() {
        Puzzle puzzle = new Puzzle("id-1", "ENC", "A hint", "SOL");
        assertEquals("A hint", puzzle.hint());
    }

    @Test
    public void puzzle_holdsSolution() {
        Puzzle puzzle = new Puzzle("id-1", "ENC", "hint", "PLAIN");
        assertEquals("PLAIN", puzzle.solution());
    }

    @Test
    public void puzzle_equality_sameValues() {
        Puzzle a = new Puzzle("id", "enc", "hint", "sol");
        Puzzle b = new Puzzle("id", "enc", "hint", "sol");
        assertEquals(a, b);
    }

    @Test
    public void puzzle_inequality_differentId() {
        assertNotEquals(new Puzzle("id-1", "enc", "hint", "sol"),
                new Puzzle("id-2", "enc", "hint", "sol"));
    }

    @Test
    public void puzzle_inequality_differentEncryptedText() {
        assertNotEquals(new Puzzle("id", "enc-A", "hint", "sol"),
                new Puzzle("id", "enc-B", "hint", "sol"));
    }

    @Test
    public void puzzle_hashCode_consistentWithEquals() {
        Puzzle a = new Puzzle("id", "enc", "hint", "sol");
        Puzzle b = new Puzzle("id", "enc", "hint", "sol");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void puzzle_toString_containsId() {
        Puzzle puzzle = new Puzzle("id-99", "ENC", "H", "SOL");
        assertTrue(puzzle.toString().contains("id-99"));
    }
}
