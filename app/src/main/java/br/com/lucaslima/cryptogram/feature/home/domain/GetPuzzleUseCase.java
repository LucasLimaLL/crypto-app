package br.com.lucaslima.cryptogram.feature.home.domain;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepository;

/**
 * Use-case that retrieves the next puzzle for the player.
 *
 * <p>Encapsulating the business rule in a dedicated use-case class keeps the
 * ViewModel thin and makes the logic unit-testable without Android dependencies.
 */
public class GetPuzzleUseCase {

    private final PuzzleRepository repository;

    public GetPuzzleUseCase(PuzzleRepository repository) {
        this.repository = repository;
    }

    /**
     * Fetches the next available puzzle.
     *
     * @return a {@link PuzzleResult} wrapping either the puzzle or an error.
     */
    public PuzzleResult execute() {
        try {
            Puzzle puzzle = repository.getNextPuzzle();
            if (puzzle != null) {
                return new PuzzleResult.Success(puzzle);
            } else {
                return new PuzzleResult.Error("No puzzles available at this time.");
            }
        } catch (Exception e) {
            return new PuzzleResult.Error(e.getMessage() != null ? e.getMessage() : "Unknown error");
        }
    }
}
