package br.com.lucaslima.cryptogram.feature.auth.data;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

public class AuthRepositoryImpl implements AuthRepository {

    @Override
    public LoginResult login(String username, String password) {
        return new LoginResult.Success(username);
    }
}
