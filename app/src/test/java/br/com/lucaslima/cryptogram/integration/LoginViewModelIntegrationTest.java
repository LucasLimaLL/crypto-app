package br.com.lucaslima.cryptogram.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;
import br.com.lucaslima.cryptogram.feature.auth.ui.LoginViewModel;

public class LoginViewModelIntegrationTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LoginViewModel viewModel =
            new LoginViewModel(new LoginUseCase(new AuthRepositoryImpl()));

    @Test
    public void initialState_isNull() {
        assertNull(viewModel.getLoginState().getValue());
    }

    @Test
    public void login_validCredentials_emitsSuccess() {
        viewModel.login("player", "pass");

        LoginResult state = viewModel.getLoginState().getValue();
        assertNotNull(state);
        assertTrue(state instanceof LoginResult.Success);
        assertEquals("player", ((LoginResult.Success) state).username());
    }

    @Test
    public void login_blankUsername_emitsError() {
        viewModel.login("", "pass");
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Error);
    }

    @Test
    public void login_nullUsername_emitsError() {
        viewModel.login(null, "pass");
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Error);
    }

    @Test
    public void login_nullPassword_emitsError() {
        viewModel.login("player", null);
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Error);
    }

    @Test
    public void login_blankPassword_emitsError() {
        viewModel.login("player", "   ");
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Error);
    }

    @Test
    public void login_afterError_canSucceedOnRetry() {
        viewModel.login("", "pass");
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Error);

        viewModel.login("player", "pass");
        assertTrue(viewModel.getLoginState().getValue() instanceof LoginResult.Success);
    }
}
