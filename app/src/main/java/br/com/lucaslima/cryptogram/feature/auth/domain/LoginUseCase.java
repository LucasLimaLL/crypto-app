package br.com.lucaslima.cryptogram.feature.auth.domain;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepository;

public class LoginUseCase {

    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

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
