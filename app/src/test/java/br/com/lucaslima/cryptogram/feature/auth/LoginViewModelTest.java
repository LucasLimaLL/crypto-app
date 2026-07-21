package br.com.lucaslima.cryptogram.feature.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;
import br.com.lucaslima.cryptogram.feature.auth.ui.LoginViewModel;

public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private LoginUseCase mockUseCase;
    private LoginViewModel viewModel;

    @Before
    public void setUp() {
        mockUseCase = mock(LoginUseCase.class);
        viewModel = new LoginViewModel(mockUseCase);
    }

    @Test
    public void initialState_isNull() {
        assertNull(viewModel.getLoginState().getValue());
    }

    @Test
    public void login_withValidCredentials_emitsSuccess() {
        when(mockUseCase.execute(anyString(), anyString()))
                .thenReturn(new LoginResult.Success("player1"));

        viewModel.login("player1", "secret");

        LoginResult state = viewModel.getLoginState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof LoginResult.Success);
        assertEquals("player1", ((LoginResult.Success) state).username());
    }

    @Test
    public void login_withInvalidCredentials_emitsInvalidCredentials() {
        when(mockUseCase.execute(anyString(), anyString()))
                .thenReturn(new LoginResult.InvalidCredentials());

        viewModel.login("player1", "wrongpassword");

        LoginResult state = viewModel.getLoginState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof LoginResult.InvalidCredentials);
    }

    @Test
    public void login_withError_emitsError() {
        when(mockUseCase.execute(anyString(), anyString()))
                .thenReturn(new LoginResult.Error("Service unavailable"));

        viewModel.login("player1", "secret");

        LoginResult state = viewModel.getLoginState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof LoginResult.Error);
        assertEquals("Service unavailable", ((LoginResult.Error) state).message());
    }

    @Test
    public void login_calledTwice_lastResultWins() {
        when(mockUseCase.execute(any(), any()))
                .thenReturn(new LoginResult.Success("first"))
                .thenReturn(new LoginResult.Error("second call error"));

        viewModel.login("user", "pass");
        viewModel.login("user", "pass");

        LoginResult state = viewModel.getLoginState().getValue();
        assertTrue(state instanceof LoginResult.Error);
    }

    @Test
    public void login_usernamePreservedInSuccessResult() {
        when(mockUseCase.execute(anyString(), anyString()))
                .thenReturn(new LoginResult.Success("uniqueName"));

        viewModel.login("uniqueName", "pass");

        assertEquals("uniqueName", ((LoginResult.Success) viewModel.getLoginState().getValue()).username());
    }
}
