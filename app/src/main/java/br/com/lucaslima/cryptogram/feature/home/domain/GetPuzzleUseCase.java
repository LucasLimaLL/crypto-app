package br.com.lucaslima.cryptogram.feature.home.domain;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepository;

public class GetPuzzleUseCase {

    private final PuzzleRepository repository;

    public GetPuzzleUseCase(PuzzleRepository repository) {
        this.repository = repository;
    }

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
