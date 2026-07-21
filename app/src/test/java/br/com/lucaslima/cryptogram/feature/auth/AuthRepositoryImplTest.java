package br.com.lucaslima.cryptogram.feature.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

public class AuthRepositoryImplTest {

    private final AuthRepositoryImpl repository = new AuthRepositoryImpl();

    @Test
    public void login_returnsSuccess() {
        LoginResult result = repository.login("alice", "secret");
        assertTrue(result instanceof LoginResult.Success);
    }

    @Test
    public void login_preservesUsernameInResult() {
        LoginResult result = repository.login("bob123", "any");
        assertEquals("bob123", ((LoginResult.Success) result).username());
    }

    @Test
    public void login_differentUsernames_preserveEach() {
        LoginResult a = repository.login("userA", "pass");
        LoginResult b = repository.login("userB", "pass");
        assertEquals("userA", ((LoginResult.Success) a).username());
        assertEquals("userB", ((LoginResult.Success) b).username());
    }
}
