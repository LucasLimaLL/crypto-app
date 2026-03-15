package br.com.lucaslima.cryptogram.feature.home.data;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

/**
 * Stub implementation of {@link PuzzleRepository}.
 *
 * <p>Returns a hard-coded puzzle for development and testing purposes.
 * Replace this class with a real network or database implementation when
 * the backend is ready. The vertical-slice boundary ensures no other
 * feature is affected by this swap.
 */
public class PuzzleRepositoryImpl implements PuzzleRepository {

    @Override
    public Puzzle getNextPuzzle() {
        return new Puzzle(
                "puzzle-001",
                "Gur dhvpx oebja sbk whzcf bire gur ynml qbt",
                "Famous English pangram — think ROT13",
                "The quick brown fox jumps over the lazy dog"
        );
    }
}
