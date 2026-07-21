package br.com.lucaslima.cryptogram.feature.home.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import br.com.lucaslima.cryptogram.R;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<HomeUiState> uiState = new MutableLiveData<>();
    private final List<HomeCategoryItem> categories;

    public HomeViewModel() {
        this(defaultCategories());
    }

    public HomeViewModel(List<HomeCategoryItem> categories) {
        this.categories = categories;
        uiState.setValue(new HomeUiState(
                HomeMode.CLASSIC,
                categories,
                categories.isEmpty() ? null : categories.get(0).getId()
        ));
    }

    public LiveData<HomeUiState> getUiState() {
        return uiState;
    }

    public void selectMode(HomeMode mode) {
        HomeUiState currentState = uiState.getValue();
        String selectedCategoryId = currentState != null ? currentState.getSelectedCategoryId() : null;
        uiState.setValue(new HomeUiState(mode, categories, selectedCategoryId));
    }

    public void selectCategory(String categoryId) {
        HomeUiState currentState = uiState.getValue();
        HomeMode selectedMode = currentState != null ? currentState.getSelectedMode() : HomeMode.THEME;
        uiState.setValue(new HomeUiState(selectedMode, categories, categoryId));
    }

    private static List<HomeCategoryItem> defaultCategories() {
        return Arrays.asList(
                new HomeCategoryItem(
                        "nature",
                        "\uD83C\uDF3F",
                        R.string.home_category_nature_title,
                        R.string.home_category_nature_subtitle
                ),
                new HomeCategoryItem(
                        "objects",
                        "\uD83D\uDD27",
                        R.string.home_category_objects_title,
                        R.string.home_category_objects_subtitle
                ),
                new HomeCategoryItem(
                        "professions",
                        "\uD83E\uDDD1\u200D\uD83D\uDCBC",
                        R.string.home_category_professions_title,
                        R.string.home_category_professions_subtitle
                ),
                new HomeCategoryItem(
                        "sports",
                        "\u26BD",
                        R.string.home_category_sports_title,
                        R.string.home_category_sports_subtitle
                ),
                new HomeCategoryItem(
                        "food",
                        "\uD83C\uDF7D\uFE0F",
                        R.string.home_category_food_title,
                        R.string.home_category_food_subtitle
                ),
                new HomeCategoryItem(
                        "science",
                        "\uD83D\uDD2C",
                        R.string.home_category_science_title,
                        R.string.home_category_science_subtitle
                )
        );
    }
}
