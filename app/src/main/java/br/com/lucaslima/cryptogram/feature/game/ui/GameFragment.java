package br.com.lucaslima.cryptogram.feature.game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.com.lucaslima.cryptogram.CryptogramApplication;
import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.FragmentGameBinding;
import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.GameUiState;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel viewModel;
    private boolean isLargeMode;
    private boolean isDaltonismMode;
    private int creditsReward;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CryptogramApplication app = (CryptogramApplication) requireActivity().getApplication();
        isLargeMode = app.isLargeMode();
        isDaltonismMode = app.isDaltonismMode();

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        creditsReward = 3;
        String modeLabel = "";
        if (getArguments() != null) {
            creditsReward = getArguments().getInt("creditsReward", 3);
            modeLabel = getArguments().getString("modeLabel", "");
        }

        binding.textPuzzleModeLabel.setText(modeLabel);
        binding.textCreditsReward.setText("+" + creditsReward + " créditos");

        binding.buttonBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.buttonGiveUp.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.buttonValidate.setOnClickListener(v -> viewModel.validatePuzzle());
        binding.buttonContinue.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        buildKeyboard();

        binding.recyclerLetters.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.getUiState().observe(getViewLifecycleOwner(), this::render);
    }

    private void render(GameUiState state) {
        if (state == null) {
            return;
        }

        List<GameLetter> letters = state.getLetters();

        updateCipherRow(letters);
        updateLetterBoxes(state);
        updateProgress(letters);
        updateFoundLetters(letters);

        if (state.isComplete()) {
            showCompletion();
        }
    }

    private void updateCipherRow(List<GameLetter> letters) {
        binding.layoutCipherRow.removeAllViews();
        for (GameLetter letter : letters) {
            TextView tv = new TextView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(42), dpToPx(42));
            params.setMarginEnd(dpToPx(2));
            tv.setLayoutParams(params);
            tv.setGravity(android.view.Gravity.CENTER);
            tv.setText(String.valueOf(letter.getCipherChar()));
            tv.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setTextColor(androidx.core.content.ContextCompat.getColor(
                    requireContext(), R.color.home_gold));
            tv.setBackground(androidx.core.content.ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_letter_tile));
            binding.layoutCipherRow.addView(tv);
        }
    }

    private void updateLetterBoxes(GameUiState state) {
        LetterBoxAdapter adapter = new LetterBoxAdapter(
                state.getLetters(),
                state.getSelectedIndex(),
                isLargeMode,
                isDaltonismMode,
                index -> viewModel.selectLetter(index)
        );
        binding.recyclerLetters.setAdapter(adapter);
    }

    private void updateProgress(List<GameLetter> letters) {
        long resolved = letters.stream()
                .filter(l -> l.isCorrect() || l.isRevealed())
                .count();
        int total = letters.size();
        int progress = total > 0 ? (int) ((resolved * 100L) / total) : 0;
        binding.progressPuzzle.setProgress(progress);
        binding.textProgress.setText(resolved + " / " + total + " letras");
    }

    private void updateFoundLetters(List<GameLetter> letters) {
        binding.layoutFoundLetters.removeAllViews();
        for (GameLetter letter : letters) {
            if (!letter.isRevealed() && !letter.isCorrect()) {
                continue;
            }
            if (letter.getGuess() == null) {
                continue;
            }
            TextView chip = new TextView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(dpToPx(8));
            chip.setLayoutParams(params);
            chip.setText(letter.getCipherChar() + "=" + letter.getGuess());
            chip.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 13f);
            chip.setTextColor(androidx.core.content.ContextCompat.getColor(
                    requireContext(), R.color.home_text_primary));
            chip.setBackground(androidx.core.content.ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_pill));
            chip.setPaddingRelative(dpToPx(10), dpToPx(5), dpToPx(10), dpToPx(5));
            binding.layoutFoundLetters.addView(chip);
        }
    }

    private void showCompletion() {
        binding.layoutGame.setVisibility(View.GONE);
        binding.layoutCompletion.setVisibility(View.VISIBLE);
        binding.textCompletionCredits.setText("+" + creditsReward + " créditos");
    }

    private void buildKeyboard() {
        String[] rows = {"ABCDEFG", "HIJKLMN", "OPQRSTU", "VWXYZ"};
        for (String row : rows) {
            LinearLayout rowLayout = new LinearLayout(requireContext());
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.topMargin = dpToPx(6);
            rowLayout.setLayoutParams(rowParams);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < row.length(); i++) {
                char letter = row.charAt(i);
                TextView key = new TextView(requireContext());
                LinearLayout.LayoutParams keyParams = new LinearLayout.LayoutParams(
                        0, dpToPx(44), 1f);
                keyParams.setMarginEnd(dpToPx(4));
                key.setLayoutParams(keyParams);
                key.setGravity(android.view.Gravity.CENTER);
                key.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 14f);
                key.setText(String.valueOf(letter));
                key.setBackground(androidx.core.content.ContextCompat.getDrawable(
                        requireContext(), R.drawable.bg_letter_tile));
                key.setTextColor(androidx.core.content.ContextCompat.getColor(
                        requireContext(), R.color.home_text_primary));
                key.setClickable(true);
                key.setFocusable(true);
                final char pressed = letter;
                key.setOnClickListener(v -> viewModel.onKeyPressed(pressed));
                rowLayout.addView(key);
            }
            binding.layoutKeyboard.addView(rowLayout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CryptogramApplication app = (CryptogramApplication) requireActivity().getApplication();
        boolean newLargeMode = app.isLargeMode();
        boolean newDaltonism = app.isDaltonismMode();
        if (newLargeMode != isLargeMode || newDaltonism != isDaltonismMode) {
            isLargeMode = newLargeMode;
            isDaltonismMode = newDaltonism;
            GameUiState current = viewModel.getUiState().getValue();
            if (current != null) {
                updateLetterBoxes(current);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * requireContext().getResources().getDisplayMetrics().density);
    }
}
