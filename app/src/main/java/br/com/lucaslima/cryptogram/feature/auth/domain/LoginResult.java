package br.com.lucaslima.cryptogram.feature.auth.domain;

public sealed interface LoginResult
        permits LoginResult.Success, LoginResult.InvalidCredentials, LoginResult.Error {

    record Success(String username) implements LoginResult {}

    record InvalidCredentials() implements LoginResult {}

    record Error(String message) implements LoginResult {}
}
