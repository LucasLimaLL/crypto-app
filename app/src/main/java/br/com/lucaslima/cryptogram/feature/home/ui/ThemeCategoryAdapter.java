package br.com.lucaslima.cryptogram.feature.home.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.ItemThemeCategoryBinding;

public class ThemeCategoryAdapter extends RecyclerView.Adapter<ThemeCategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClicked(HomeCategoryItem item);
    }

    private final List<HomeCategoryItem> items = new ArrayList<>();
    private final OnCategoryClickListener clickListener;
    private String selectedCategoryId;

    public ThemeCategoryAdapter(OnCategoryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<HomeCategoryItem> newItems, String newSelectedCategoryId) {
        items.clear();
        items.addAll(newItems);
        selectedCategoryId = newSelectedCategoryId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CategoryViewHolder(ItemThemeCategoryBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        HomeCategoryItem item = items.get(position);
        boolean selected = item.getId().equals(selectedCategoryId);
        holder.bind(item, selected, clickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static final class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final ItemThemeCategoryBinding binding;

        CategoryViewHolder(ItemThemeCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HomeCategoryItem item, boolean selected, OnCategoryClickListener clickListener) {
            binding.textCategoryIcon.setText(item.getIconGlyph());
            binding.textCategoryTitle.setText(item.getTitleRes());
            binding.textCategorySubtitle.setText(item.getSubtitleRes());

            int backgroundColor = ContextCompat.getColor(
                    binding.getRoot().getContext(),
                    selected ? R.color.home_theme_card_selected_bg : R.color.home_theme_card_bg
            );
            int strokeColor = ContextCompat.getColor(
                    binding.getRoot().getContext(),
                    selected ? R.color.home_theme_card_selected_stroke : R.color.home_theme_card_stroke
            );
            int iconColor = ContextCompat.getColor(
                    binding.getRoot().getContext(),
                    selected ? R.color.home_gold : R.color.home_theme_icon
            );

            binding.cardCategory.setCardBackgroundColor(backgroundColor);
            binding.cardCategory.setStrokeColor(strokeColor);
            binding.cardCategory.setStrokeWidth(selected ? dpToPx(binding, 2) : dpToPx(binding, 1));
            binding.textCategoryIcon.setTextColor(iconColor);
            binding.cardCategory.setOnClickListener(v -> clickListener.onCategoryClicked(item));
        }

        private int dpToPx(ItemThemeCategoryBinding binding, int dp) {
            float density = binding.getRoot().getResources().getDisplayMetrics().density;
            return Math.round(dp * density);
        }
    }
}
