package br.com.lucaslima.cryptogram.feature.home.domain;

public sealed interface PuzzleResult
        permits PuzzleResult.Loading, PuzzleResult.Success, PuzzleResult.Error {

    record Loading() implements PuzzleResult {}

    record Success(Puzzle puzzle) implements PuzzleResult {}

    record Error(String message) implements PuzzleResult {}
}
