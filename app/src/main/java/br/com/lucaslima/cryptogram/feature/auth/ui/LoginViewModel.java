package br.com.lucaslima.cryptogram.feature.auth.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;

/**
 * ViewModel for the Login screen.
 *
 * <p>Holds form-input state across rotation and exposes a single
 * {@link LiveData} stream of {@link LoginResult} that the Fragment observes.
 */
public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginResult> loginState = new MutableLiveData<>();
    private final LoginUseCase loginUseCase;

    public LoginViewModel() {
        this(new LoginUseCase(new AuthRepositoryImpl()));
    }

    LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    /** Exposes login outcome to the UI. */
    public LiveData<LoginResult> getLoginState() {
        return loginState;
    }

    /**
     * Initiates a login attempt with the given credentials.
     *
     * @param username the player's username
     * @param password the player's password
     */
    public void login(String username, String password) {
        LoginResult result = loginUseCase.execute(username, password);
        loginState.setValue(result);
    }
}
