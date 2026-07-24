package br.com.lucaslima.cryptogram.feature.puzzle.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.FragmentPuzzleBinding;
import br.com.lucaslima.cryptogram.feature.credits.CreditsManager;

public class PuzzleFragment extends Fragment {

    private static final String CIPHER_STR = "DNFZK GZQNP";
    private static final String PLAIN_STR = "VAMOS JOGAR";
    private static final char[] REVEALED_CIPHER = {'D', 'N', 'K'};
    private static final char[] PLAIN_FOUND = {'V', 'A', 'S'};

    private FragmentPuzzleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPuzzleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int creditsReward = 3;
        String modeLabel = "";
        if (getArguments() != null) {
            creditsReward = getArguments().getInt("creditsReward", 3);
            modeLabel = getArguments().getString("modeLabel", "");
        }

        binding.textPuzzleModeLabel.setText(modeLabel);
        binding.textCreditsReward.setText("+" + creditsReward + " créditos");
        binding.textProgress.setText("5 / 10 letras");

        buildCipherDisplay();
        buildFoundLettersChips();
        buildKeyboard();

        final int reward = creditsReward;
        binding.buttonBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.buttonGiveUp.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.buttonComplete.setOnClickListener(v -> completeGame(reward));
        binding.buttonContinue.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    private void buildCipherDisplay() {
        for (int i = 0; i < CIPHER_STR.length(); i++) {
            char cipherChar = CIPHER_STR.charAt(i);
            if (cipherChar == ' ') {
                View spacerCipher = new View(requireContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(16), dpToPx(42));
                binding.layoutCipherRow.addView(spacerCipher, params);

                View spacerDecoded = new View(requireContext());
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(dpToPx(16), dpToPx(42));
                binding.layoutDecodedRow.addView(spacerDecoded, params2);
            } else {
                boolean revealed = isRevealed(cipherChar);
                int plainIndex = indexOf(cipherChar);
                char plainChar = revealed ? PLAIN_FOUND[plainIndex] : '_';

                binding.layoutCipherRow.addView(letterTile(String.valueOf(cipherChar), true, false));
                binding.layoutDecodedRow.addView(letterTile(String.valueOf(plainChar), false, revealed));
            }
        }
    }

    private int indexOf(char cipherChar) {
        for (int i = 0; i < REVEALED_CIPHER.length; i++) {
            if (REVEALED_CIPHER[i] == cipherChar) return i;
        }
        return -1;
    }

    private TextView letterTile(String text, boolean isCipher, boolean revealed) {
        TextView tv = new TextView(requireContext());
        int size = dpToPx(42);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMarginEnd(dpToPx(4));
        tv.setLayoutParams(params);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setText(text);
        tv.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, isCipher ? 16f : 14f);

        int textColorRes;
        if (isCipher) {
            textColorRes = R.color.home_gold;
        } else if (revealed) {
            textColorRes = R.color.home_text_primary;
        } else {
            textColorRes = R.color.home_text_secondary;
        }
        tv.setTextColor(ContextCompat.getColor(requireContext(), textColorRes));

        tv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_letter_tile));
        int bgTintRes = isCipher ? R.color.home_surface_alt_2 : R.color.home_surface_alt;
        tv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgTintRes)));

        return tv;
    }

    private void buildFoundLettersChips() {
        for (int i = 0; i < REVEALED_CIPHER.length; i++) {
            TextView chip = new TextView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(dpToPx(8));
            chip.setLayoutParams(params);
            chip.setText(REVEALED_CIPHER[i] + "=" + PLAIN_FOUND[i]);
            chip.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 13f);
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_text_primary));
            chip.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_pill));
            chip.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.home_surface_alt)));
            chip.setPaddingRelative(dpToPx(10), dpToPx(5), dpToPx(10), dpToPx(5));
            binding.layoutFoundLetters.addView(chip);
        }
    }

    private void buildKeyboard() {
        String[] rows = {"ABCDEFG", "HIJKLMN", "OPQRSTU", "VWXYZ"};
        for (String row : rows) {
            LinearLayout rowLayout = new LinearLayout(requireContext());
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            rowParams.topMargin = dpToPx(6);
            rowLayout.setLayoutParams(rowParams);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < row.length(); i++) {
                char letter = row.charAt(i);
                TextView key = new TextView(requireContext());
                LinearLayout.LayoutParams keyParams = new LinearLayout.LayoutParams(
                        0,
                        dpToPx(44),
                        1f
                );
                keyParams.setMarginEnd(dpToPx(4));
                key.setLayoutParams(keyParams);
                key.setGravity(android.view.Gravity.CENTER);
                key.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 14f);
                key.setText(String.valueOf(letter));
                key.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_letter_tile));

                if (isRevealed(letter)) {
                    key.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_text_tertiary));
                    key.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.home_border_soft)));
                } else {
                    key.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_text_primary));
                    key.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.home_surface_alt)));
                    key.setClickable(true);
                    key.setFocusable(true);
                    key.setOnClickListener(v -> {
                        key.setBackgroundTintList(ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.primary_container)));
                        key.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_gold));
                    });
                }

                rowLayout.addView(key);
            }

            binding.layoutKeyboard.addView(rowLayout);
        }
    }

    private void completeGame(int reward) {
        CreditsManager.getInstance(requireContext()).recordPuzzleCompleted(reward);
        binding.layoutGame.setVisibility(View.GONE);
        binding.layoutCompletion.setVisibility(View.VISIBLE);
        binding.textCompletionCredits.setText("+" + reward + " créditos");
    }

    private boolean isRevealed(char c) {
        for (char rc : REVEALED_CIPHER) {
            if (rc == c) return true;
        }
        return false;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * requireContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
