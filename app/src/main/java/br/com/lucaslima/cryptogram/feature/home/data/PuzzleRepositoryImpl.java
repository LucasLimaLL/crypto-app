package br.com.lucaslima.cryptogram.feature.home.data;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

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
