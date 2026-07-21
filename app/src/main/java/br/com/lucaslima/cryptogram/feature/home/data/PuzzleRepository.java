package br.com.lucaslima.cryptogram.feature.home.data;

import br.com.lucaslima.cryptogram.feature.home.domain.Puzzle;

public interface PuzzleRepository {

    Puzzle getNextPuzzle();
}
