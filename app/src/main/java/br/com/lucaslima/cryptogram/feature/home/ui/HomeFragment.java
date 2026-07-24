package br.com.lucaslima.cryptogram.feature.home.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.button.MaterialButton;

import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.FragmentHomeBinding;
import br.com.lucaslima.cryptogram.feature.credits.CreditsManager;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ThemeCategoryAdapter categoryAdapter;

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
        setupRecyclerView();
        setupInteractions();
        observeUiState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            CreditsManager cm = CreditsManager.getInstance(requireContext());
            binding.textCreditsHeader.setText("🏆 " + cm.getBalance() + " créditos");
            binding.textDailyStreak.setText("Sequência: " + cm.getStreak() + " dias 🔥");
        }
    }

    private void setupRecyclerView() {
        categoryAdapter = new ThemeCategoryAdapter(item -> {
            viewModel.selectCategory(item.getId());
            navigateToPuzzle(4, getString(R.string.home_mode_theme) + " · " + getString(item.getTitleRes()));
        });
        binding.recyclerCategories.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerCategories.setAdapter(categoryAdapter);
        binding.recyclerCategories.setNestedScrollingEnabled(false);
    }

    private void setupInteractions() {
        binding.buttonProfile.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_profileFragment));
        binding.cardDailyChallenge.setOnClickListener(v ->
                navigateToPuzzle(2, getString(R.string.home_daily_title)));

        binding.buttonModeClassic.setOnClickListener(v -> viewModel.selectMode(HomeMode.CLASSIC));
        binding.buttonModeTimed.setOnClickListener(v -> viewModel.selectMode(HomeMode.TIMED));
        binding.buttonModeTheme.setOnClickListener(v -> viewModel.selectMode(HomeMode.THEME));

        binding.cardClassicEasy.setOnClickListener(v ->
                navigateToPuzzle(3, "Clássico · " + getString(R.string.home_easy_title)));
        binding.cardClassicMedium.setOnClickListener(v ->
                navigateToPuzzle(5, "Clássico · " + getString(R.string.home_medium_title)));
        binding.cardClassicHard.setOnClickListener(v ->
                navigateToPuzzle(8, "Clássico · " + getString(R.string.home_hard_title)));

        binding.cardTimedHard.setOnClickListener(v ->
                navigateToPuzzle(8, "VS · " + getString(R.string.home_timed_hard_title)));
        binding.cardTimedMedium.setOnClickListener(v ->
                navigateToPuzzle(5, "VS · " + getString(R.string.home_timed_medium_title)));
        binding.cardTimedEasy.setOnClickListener(v ->
                navigateToPuzzle(3, "VS · " + getString(R.string.home_timed_easy_title)));
    }

    private void navigateToPuzzle(int creditsReward, String modeLabel) {
        Bundle args = new Bundle();
        args.putInt("creditsReward", creditsReward);
        args.putString("modeLabel", modeLabel);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_puzzleFragment, args);
    }

    private void observeUiState() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), this::render);
    }

    private void render(HomeUiState state) {
        HomeMode selectedMode = state.getSelectedMode();
        updateModeButtons(selectedMode);
        updateVisibleSection(selectedMode);
        categoryAdapter.submitList(state.getCategories(), state.getSelectedCategoryId());
    }

    private void updateModeButtons(HomeMode selectedMode) {
        applyModeStyle(binding.buttonModeClassic, selectedMode == HomeMode.CLASSIC);
        applyModeStyle(binding.buttonModeTimed, selectedMode == HomeMode.TIMED);
        applyModeStyle(binding.buttonModeTheme, selectedMode == HomeMode.THEME);
    }

    private void updateVisibleSection(HomeMode selectedMode) {
        switch (selectedMode) {
            case CLASSIC:
                binding.textSectionLabel.setText(R.string.home_section_classic);
                binding.textSectionDescription.setVisibility(View.GONE);
                binding.containerClassicMode.setVisibility(View.VISIBLE);
                binding.containerTimedMode.setVisibility(View.GONE);
                binding.containerThemeMode.setVisibility(View.GONE);
                break;
            case TIMED:
                binding.textSectionLabel.setText(R.string.home_section_timed);
                binding.textSectionDescription.setText(R.string.home_section_timed_description);
                binding.textSectionDescription.setVisibility(View.VISIBLE);
                binding.containerClassicMode.setVisibility(View.GONE);
                binding.containerTimedMode.setVisibility(View.VISIBLE);
                binding.containerThemeMode.setVisibility(View.GONE);
                break;
            case THEME:
                binding.textSectionLabel.setText(R.string.home_section_theme);
                binding.textSectionDescription.setText(R.string.home_section_theme_description);
                binding.textSectionDescription.setVisibility(View.VISIBLE);
                binding.containerClassicMode.setVisibility(View.GONE);
                binding.containerTimedMode.setVisibility(View.GONE);
                binding.containerThemeMode.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void applyModeStyle(MaterialButton button, boolean selected) {
        int backgroundColor = ContextCompat.getColor(
                requireContext(),
                selected ? R.color.home_mode_selected_bg : android.R.color.transparent
        );
        int textColor = ContextCompat.getColor(
                requireContext(),
                selected ? R.color.home_mode_selected_text : R.color.home_mode_unselected_text
        );

        button.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        button.setTextColor(textColor);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
