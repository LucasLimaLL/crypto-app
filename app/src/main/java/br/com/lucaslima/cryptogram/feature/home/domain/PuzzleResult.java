package br.com.lucaslima.cryptogram.feature.home.domain;

/**
 * Sealed interface representing the possible states of a puzzle-loading operation.
 *
 * <p>Using a Java 17+ {@code sealed} interface with nested {@code record} subtypes
 * lets callers use exhaustive pattern-matching ({@code switch} expressions in
 * Java 21) instead of fragile {@code instanceof} chains.
 *
 * <pre>{@code
 * switch (result) {
 *     case PuzzleResult.Loading()       -> showSpinner();
 *     case PuzzleResult.Success(var p)  -> showPuzzle(p);
 *     case PuzzleResult.Error(var msg)  -> showError(msg);
 * }
 * }</pre>
 */
public sealed interface PuzzleResult
        permits PuzzleResult.Loading, PuzzleResult.Success, PuzzleResult.Error {

    /** The data layer is still fetching the puzzle. */
    record Loading() implements PuzzleResult {}

    /** A puzzle was retrieved successfully. */
    record Success(Puzzle puzzle) implements PuzzleResult {}

    /** The fetch failed; {@code message} contains a human-readable reason. */
    record Error(String message) implements PuzzleResult {}
}
