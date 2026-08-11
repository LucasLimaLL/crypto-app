package br.com.lucaslima.cryptogram.feature.game.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.lucaslima.cryptogram.databinding.ItemLetterBoxBinding;
import br.com.lucaslima.cryptogram.databinding.ItemLetterBoxLargeBinding;
import br.com.lucaslima.cryptogram.feature.game.domain.GameLetter;
import br.com.lucaslima.cryptogram.feature.game.domain.LetterState;

public class LetterBoxAdapter extends RecyclerView.Adapter<LetterBoxAdapter.LetterBoxViewHolder> {

    public interface OnLetterClickListener {
        void onLetterClicked(int index);
    }

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_LARGE = 1;

    private static final String COLOR_SELECTED = "#FFFFFF";
    private static final String COLOR_REVEALED = "#FFC93C";
    private static final String COLOR_CORRECT = "#4CAF50";
    private static final String COLOR_WRONG = "#C62828";
    private static final String COLOR_DALTONISM_CORRECT = "#2196F3";
    private static final String COLOR_DALTONISM_WRONG = "#FF6B35";
    private static final String ICON_CORRECT = "✓";
    private static final String ICON_WRONG = "✗";

    private final List<GameLetter> letters;
    private final int selectedIndex;
    private final boolean isLargeMode;
    private final boolean isDaltonismMode;
    private final OnLetterClickListener listener;

    public LetterBoxAdapter(List<GameLetter> letters, int selectedIndex,
                             boolean isLargeMode, boolean isDaltonismMode,
                             OnLetterClickListener listener) {
        this.letters = letters;
        this.selectedIndex = selectedIndex;
        this.isLargeMode = isLargeMode;
        this.isDaltonismMode = isDaltonismMode;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return isLargeMode ? TYPE_LARGE : TYPE_NORMAL;
    }

    @NonNull
    @Override
    public LetterBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_LARGE) {
            ItemLetterBoxLargeBinding binding = ItemLetterBoxLargeBinding.inflate(inflater, parent, false);
            applyLargeModeSize(binding.getRoot(), letters.size());
            return new LetterBoxViewHolder(binding.getRoot(), binding.tvCipherChar,
                    binding.tvLetter, binding.tvIcon, isDaltonismMode);
        }
        ItemLetterBoxBinding binding = ItemLetterBoxBinding.inflate(inflater, parent, false);
        return new LetterBoxViewHolder(binding.getRoot(), binding.tvCipherChar,
                binding.tvLetter, binding.tvIcon, isDaltonismMode);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterBoxViewHolder holder, int position) {
        GameLetter letter = letters.get(position);
        LetterState state = letter.computeState(selectedIndex);
        holder.bind(letter, state, position, listener);
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    private static void applyLargeModeSize(View root, int wordLength) {
        int sizeDp;
        if (wordLength <= 6) {
            sizeDp = 80;
        } else if (wordLength <= 9) {
            sizeDp = 64;
        } else {
            sizeDp = 48;
        }
        float density = root.getContext().getResources().getDisplayMetrics().density;
        int sizePx = Math.round(sizeDp * density);
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = sizePx;
        params.height = sizePx;
        root.setLayoutParams(params);
    }

    static class LetterBoxViewHolder extends RecyclerView.ViewHolder {

        private final View root;
        private final TextView tvCipherChar;
        private final TextView tvLetter;
        private final TextView tvIcon;
        private final boolean isDaltonismMode;

        LetterBoxViewHolder(View root, TextView tvCipherChar, TextView tvLetter,
                             TextView tvIcon, boolean isDaltonismMode) {
            super(root);
            this.root = root;
            this.tvCipherChar = tvCipherChar;
            this.tvLetter = tvLetter;
            this.tvIcon = tvIcon;
            this.isDaltonismMode = isDaltonismMode;
        }

        void bind(GameLetter letter, LetterState state, int position,
                  OnLetterClickListener listener) {
            tvCipherChar.setText(String.valueOf(letter.getCipherChar()));
            Character guess = letter.getGuess();
            tvLetter.setText(guess != null ? String.valueOf(guess) : "");
            applyStateColors(state);
            applyDaltonismIcon(state);
            root.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLetterClicked(position);
                }
            });
        }

        private void applyStateColors(LetterState state) {
            int borderColor = resolveBorderColor(state);
            int textColor = resolveTextColor(state);
            root.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(borderColor));
            tvLetter.setTextColor(textColor);
        }

        private int resolveBorderColor(LetterState state) {
            switch (state) {
                case SELECTED:
                    return Color.parseColor(COLOR_SELECTED);
                case REVEALED:
                    return Color.parseColor(COLOR_REVEALED);
                case CORRECT:
                    return isDaltonismMode
                            ? Color.parseColor(COLOR_DALTONISM_CORRECT)
                            : Color.parseColor(COLOR_CORRECT);
                case WRONG:
                    return isDaltonismMode
                            ? Color.parseColor(COLOR_DALTONISM_WRONG)
                            : Color.parseColor(COLOR_WRONG);
                case FILLED:
                case EMPTY:
                    return Color.TRANSPARENT;
                default:
                    return Color.TRANSPARENT;
            }
        }

        private int resolveTextColor(LetterState state) {
            switch (state) {
                case REVEALED:
                    return Color.parseColor(COLOR_REVEALED);
                case CORRECT:
                    return isDaltonismMode
                            ? Color.parseColor(COLOR_DALTONISM_CORRECT)
                            : Color.parseColor(COLOR_CORRECT);
                case WRONG:
                    return isDaltonismMode
                            ? Color.parseColor(COLOR_DALTONISM_WRONG)
                            : Color.parseColor(COLOR_WRONG);
                case SELECTED:
                case FILLED:
                case EMPTY:
                    return Color.WHITE;
                default:
                    return Color.WHITE;
            }
        }

        private void applyDaltonismIcon(LetterState state) {
            if (!isDaltonismMode) {
                tvIcon.setVisibility(View.GONE);
                return;
            }
            if (state == LetterState.CORRECT) {
                tvIcon.setVisibility(View.VISIBLE);
                tvIcon.setText(ICON_CORRECT);
                tvIcon.setTextColor(Color.parseColor(COLOR_DALTONISM_CORRECT));
            } else if (state == LetterState.WRONG) {
                tvIcon.setVisibility(View.VISIBLE);
                tvIcon.setText(ICON_WRONG);
                tvIcon.setTextColor(Color.parseColor(COLOR_DALTONISM_WRONG));
            } else {
                tvIcon.setVisibility(View.GONE);
            }
        }
    }
}
