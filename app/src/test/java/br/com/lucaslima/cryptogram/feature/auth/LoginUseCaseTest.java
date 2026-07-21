package br.com.lucaslima.cryptogram.feature.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepository;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;

public class LoginUseCaseTest {

    private AuthRepository mockRepository;
    private LoginUseCase useCase;

    @Before
    public void setUp() {
        mockRepository = mock(AuthRepository.class);
        useCase = new LoginUseCase(mockRepository);
    }

    @Test
    public void execute_nullUsername_returnsError() {
        LoginResult result = useCase.execute(null, "password");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Username must not be empty.", ((LoginResult.Error) result).message());
        verify(mockRepository, never()).login(any(), any());
    }

    @Test
    public void execute_blankUsername_returnsError() {
        LoginResult result = useCase.execute("   ", "password");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Username must not be empty.", ((LoginResult.Error) result).message());
        verify(mockRepository, never()).login(any(), any());
    }

    @Test
    public void execute_emptyUsername_returnsError() {
        LoginResult result = useCase.execute("", "password");
        assertTrue(result instanceof LoginResult.Error);
        verify(mockRepository, never()).login(any(), any());
    }

    @Test
    public void execute_nullPassword_returnsError() {
        LoginResult result = useCase.execute("user", null);
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Password must not be empty.", ((LoginResult.Error) result).message());
        verify(mockRepository, never()).login(any(), any());
    }

    @Test
    public void execute_blankPassword_returnsError() {
        LoginResult result = useCase.execute("user", "  ");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Password must not be empty.", ((LoginResult.Error) result).message());
        verify(mockRepository, never()).login(any(), any());
    }

    @Test
    public void execute_emptyPassword_returnsError() {
        LoginResult result = useCase.execute("user", "");
        assertTrue(result instanceof LoginResult.Error);
    }

    @Test
    public void execute_validCredentials_delegatesToRepository() {
        when(mockRepository.login("user", "pass")).thenReturn(new LoginResult.Success("user"));
        LoginResult result = useCase.execute("user", "pass");
        assertTrue(result instanceof LoginResult.Success);
        assertEquals("user", ((LoginResult.Success) result).username());
    }

    @Test
    public void execute_repositoryReturnsInvalidCredentials_propagates() {
        when(mockRepository.login(any(), any())).thenReturn(new LoginResult.InvalidCredentials());
        LoginResult result = useCase.execute("user", "pass");
        assertTrue(result instanceof LoginResult.InvalidCredentials);
    }

    @Test
    public void execute_repositoryThrowsWithMessage_returnsError() {
        when(mockRepository.login(any(), any())).thenThrow(new RuntimeException("Network failure"));
        LoginResult result = useCase.execute("user", "pass");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Network failure", ((LoginResult.Error) result).message());
    }

    @Test
    public void execute_repositoryThrowsWithNullMessage_returnsUnknownError() {
        when(mockRepository.login(any(), any())).thenThrow(new RuntimeException((String) null));
        LoginResult result = useCase.execute("user", "pass");
        assertTrue(result instanceof LoginResult.Error);
        assertEquals("Unknown error", ((LoginResult.Error) result).message());
    }
}
