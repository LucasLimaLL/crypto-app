package br.com.lucaslima.cryptogram.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;

public class LoginFlowIntegrationTest {

    private final LoginUseCase useCase = new LoginUseCase(new AuthRepositoryImpl());

    @Test
    public void login_validCredentials_returnsSuccess() {
        LoginResult result = useCase.execute("alice", "password123");
        assertTrue(result instanceof LoginResult.Success);
        assertEquals("alice", ((LoginResult.Success) result).username());
    }

    @Test
    public void login_emptyUsername_returnsError() {
        LoginResult result = useCase.execute("", "password");
        assertTrue(result instanceof LoginResult.Error);
    }

    @Test
    public void login_blankUsername_returnsError() {
        LoginResult result = useCase.execute("   ", "password");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Username must not be empty.", ((LoginResult.Error) result).message());
    }

    @Test
    public void login_nullUsername_returnsError() {
        LoginResult result = useCase.execute(null, "password");
        assertTrue(result instanceof LoginResult.Error);
    }

    @Test
    public void login_emptyPassword_returnsError() {
        LoginResult result = useCase.execute("alice", "");
        assertTrue(result instanceof LoginResult.Error);
    }

    @Test
    public void login_blankPassword_returnsError() {
        LoginResult result = useCase.execute("alice", "   ");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Password must not be empty.", ((LoginResult.Error) result).message());
    }

    @Test
    public void login_nullPassword_returnsError() {
        LoginResult result = useCase.execute("alice", null);
        assertTrue(result instanceof LoginResult.Error);
    }

    @Test
    public void login_successPreservesExactUsername() {
        LoginResult result = useCase.execute("Player_123", "any_pass");
        assertEquals("Player_123", ((LoginResult.Success) result).username());
    }
}
