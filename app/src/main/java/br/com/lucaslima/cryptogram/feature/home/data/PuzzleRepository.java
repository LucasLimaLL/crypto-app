package br.com.lucaslima.cryptogram.feature.home.data;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

/**
 * Contract for retrieving puzzle data.
 *
 * <p>Defining the repository as an interface allows the domain and UI layers to
 * depend on an abstraction. Concrete implementations (network, local database,
 * in-memory stub) are swapped without touching the callers.
 */
public interface PuzzleRepository {

    /**
     * Returns the next unsolved puzzle for the current player, or {@code null}
     * if none are available.
     */
    Puzzle getNextPuzzle();
}
