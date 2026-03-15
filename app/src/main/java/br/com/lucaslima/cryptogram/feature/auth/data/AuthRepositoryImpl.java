package br.com.lucaslima.cryptogram.feature.auth.data;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

/**
 * Stub implementation of {@link AuthRepository}.
 *
 * <p>Accepts any non-empty credentials for local development and demo builds.
 * Replace with a real network call (e.g., Retrofit + OAuth2) when the
 * authentication backend is ready. All callers depend on the interface, so
 * swapping this class has zero impact on other layers.
 */
public class AuthRepositoryImpl implements AuthRepository {

    @Override
    public LoginResult login(String username, String password) {
        // Stub: treat any non-empty credentials as valid for local development.
        return new LoginResult.Success(username);
    }
}
