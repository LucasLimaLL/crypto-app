package br.com.lucaslima.cryptogram.feature.auth.domain;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepository;

/**
 * Use-case that authenticates a player with username and password.
 *
 * <p>Encapsulating validation rules here prevents them from leaking into the
 * ViewModel or Fragment, and makes them straightforward to test in isolation.
 */
public class LoginUseCase {

    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Attempts to authenticate the user with the supplied credentials.
     *
     * @param username the player's username — must not be blank
     * @param password the player's password — must not be blank
     * @return a {@link LoginResult} describing the outcome
     */
    public LoginResult execute(String username, String password) {
        if (username == null || username.isBlank()) {
            return new LoginResult.Error("Username must not be empty.");
        }
        if (password == null || password.isBlank()) {
            return new LoginResult.Error("Password must not be empty.");
        }
        try {
            return authRepository.login(username, password);
        } catch (Exception e) {
            return new LoginResult.Error(
                    e.getMessage() != null ? e.getMessage() : "Unknown error"
            );
        }
    }
}
