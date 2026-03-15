package br.com.lucaslima.cryptogram.feature.auth.data;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

/**
 * Contract for authentication operations.
 *
 * <p>Decoupling the domain from the concrete data source means the login
 * flow can be tested with a fast in-memory stub while a network or OAuth
 * implementation is used in production.
 */
public interface AuthRepository {

    /**
     * Authenticates a user by username and password.
     *
     * @param username the player's username
     * @param password the player's password
     * @return a {@link LoginResult} describing the outcome
     */
    LoginResult login(String username, String password);
}
