package br.com.lucaslima.cryptogram.feature.auth.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.lucaslima.cryptogram.feature.auth.data.AuthRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginUseCase;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginResult> loginState = new MutableLiveData<>();
    private final LoginUseCase loginUseCase;

    public LoginViewModel() {
        this(new LoginUseCase(new AuthRepositoryImpl()));
    }

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public LiveData<LoginResult> getLoginState() {
        return loginState;
    }

    public void login(String username, String password) {
        LoginResult result = loginUseCase.execute(username, password);
        loginState.setValue(result);
    }
}
