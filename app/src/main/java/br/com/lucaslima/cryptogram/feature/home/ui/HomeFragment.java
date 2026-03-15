package br.com.lucaslima.cryptogram.feature.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import br.com.lucaslima.cryptogram.databinding.FragmentHomeBinding;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;

/**
 * Fragment for the Home / game-board screen.
 *
 * <p>Observes {@link HomeViewModel#getPuzzleState()} and renders the appropriate
 * UI state (loading spinner, puzzle text, or error message). All business logic
 * stays in the ViewModel; the Fragment only translates state into views.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        observeViewModel();
        viewModel.loadPuzzle();
    }

    private void observeViewModel() {
        viewModel.getPuzzleState().observe(getViewLifecycleOwner(), result -> {
            switch (result) {
                case PuzzleResult.Loading ignored -> showLoading();
                case PuzzleResult.Success success  -> showPuzzle(success.puzzle().encryptedText());
                case PuzzleResult.Error error      -> showError(error.message());
            }
        });
    }

    private void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.textPuzzle.setVisibility(View.GONE);
        binding.textError.setVisibility(View.GONE);
    }

    private void showPuzzle(String encryptedText) {
        binding.progressBar.setVisibility(View.GONE);
        binding.textError.setVisibility(View.GONE);
        binding.textPuzzle.setVisibility(View.VISIBLE);
        binding.textPuzzle.setText(encryptedText);
    }

    private void showError(String message) {
        binding.progressBar.setVisibility(View.GONE);
        binding.textPuzzle.setVisibility(View.GONE);
        binding.textError.setVisibility(View.VISIBLE);
        binding.textError.setText(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
