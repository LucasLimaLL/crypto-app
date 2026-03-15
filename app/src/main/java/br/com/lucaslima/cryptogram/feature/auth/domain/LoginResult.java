package br.com.lucaslima.cryptogram.feature.auth.domain;

/**
 * Sealed interface representing the outcome of a login attempt.
 *
 * <p>Callers can use exhaustive {@code switch} expressions (Java 21) to handle
 * every possible outcome without a default branch, improving compile-time safety.
 */
public sealed interface LoginResult
        permits LoginResult.Success, LoginResult.InvalidCredentials, LoginResult.Error {

    /** Login succeeded; {@code username} identifies the authenticated user. */
    record Success(String username) implements LoginResult {}

    /** The provided credentials were rejected by the server. */
    record InvalidCredentials() implements LoginResult {}

    /** An unexpected error occurred; {@code message} provides details. */
    record Error(String message) implements LoginResult {}
}
