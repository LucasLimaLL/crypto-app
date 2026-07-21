package br.com.lucaslima.cryptogram.feature.auth.data;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

public interface AuthRepository {

    LoginResult login(String username, String password);
}
