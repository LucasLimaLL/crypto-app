package br.com.lucaslima.cryptogram.feature.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.FragmentLoginBinding;
import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

/**
 * Fragment for the Login screen.
 *
 * <p>Collects username/password, delegates authentication to {@link LoginViewModel},
 * and navigates to the Home screen on success.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupListeners();
    }

    private void observeViewModel() {
        viewModel.getLoginState().observe(getViewLifecycleOwner(), result -> {
            switch (result) {
                case LoginResult.Success ignored ->
                        Navigation.findNavController(requireView())
                                .navigate(R.id.action_loginFragment_to_homeFragment);
                case LoginResult.InvalidCredentials ignored ->
                        binding.textError.setText(R.string.error_invalid_credentials);
                case LoginResult.Error error ->
                        binding.textError.setText(error.message());
            }
            binding.textError.setVisibility(
                    result instanceof LoginResult.Success ? View.GONE : View.VISIBLE);
        });
    }

    private void setupListeners() {
        binding.buttonLogin.setOnClickListener(v -> {
            String username = binding.editUsername.getText() != null
                    ? binding.editUsername.getText().toString().trim() : "";
            String password = binding.editPassword.getText() != null
                    ? binding.editPassword.getText().toString().trim() : "";
            viewModel.login(username, password);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
