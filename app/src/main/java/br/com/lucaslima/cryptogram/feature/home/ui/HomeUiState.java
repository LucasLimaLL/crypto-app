package br.com.lucaslima.cryptogram.feature.home.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HomeUiState {

    private final HomeMode selectedMode;
    private final List<HomeCategoryItem> categories;
    private final String selectedCategoryId;

    public HomeUiState(HomeMode selectedMode, List<HomeCategoryItem> categories, String selectedCategoryId) {
        this.selectedMode = selectedMode;
        this.categories = Collections.unmodifiableList(new ArrayList<>(categories));
        this.selectedCategoryId = selectedCategoryId;
    }

    public HomeMode getSelectedMode() {
        return selectedMode;
    }

    public List<HomeCategoryItem> getCategories() {
        return categories;
    }

    public String getSelectedCategoryId() {
        return selectedCategoryId;
    }
}
