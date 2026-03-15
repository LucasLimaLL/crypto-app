package br.com.lucaslima.cryptogram.feature.home.domain;

/**
 * Represents a single cryptogram puzzle.
 *
 * <p>Using a Java 21 {@code record} gives us an immutable value object with
 * auto-generated constructor, accessors, {@code equals}, {@code hashCode}, and
 * {@code toString} — no boilerplate required.
 *
 * @param id           unique puzzle identifier
 * @param encryptedText the ciphertext the player must decode
 * @param hint         an optional hint shown when the player asks for help
 * @param solution     the plaintext solution (never exposed to the UI layer)
 */
public record Puzzle(
        String id,
        String encryptedText,
        String hint,
        String solution
) {}
