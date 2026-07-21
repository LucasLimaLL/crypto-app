package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import br.com.lucaslima.cryptogram.feature.home.ui.HomeCategoryItem;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeMode;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeUiState;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeViewModel;

public class HomeViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void defaultState_startsInClassicModeAndSelectsFirstCategory() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0),
                new HomeCategoryItem("sports", "S", 0, 0)
        ));

        HomeUiState state = viewModel.getUiState().getValue();

        assertNotNull(state);
        assertEquals(HomeMode.CLASSIC, state.getSelectedMode());
        assertEquals("nature", state.getSelectedCategoryId());
        assertEquals(2, state.getCategories().size());
    }

    @Test
    public void defaultState_withEmptyList_selectedCategoryIdIsNull() {
        HomeViewModel viewModel = new HomeViewModel(Collections.emptyList());

        HomeUiState state = viewModel.getUiState().getValue();

        assertNotNull(state);
        assertNull(state.getSelectedCategoryId());
    }

    @Test
    public void selectMode_updatesSelectedMode() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0)
        ));

        viewModel.selectMode(HomeMode.THEME);

        HomeUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals(HomeMode.THEME, state.getSelectedMode());
    }

    @Test
    public void selectMode_preservesSelectedCategoryId() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0),
                new HomeCategoryItem("sports", "S", 0, 0)
        ));
        viewModel.selectCategory("sports");

        viewModel.selectMode(HomeMode.TIMED);

        HomeUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals("sports", state.getSelectedCategoryId());
        assertEquals(HomeMode.TIMED, state.getSelectedMode());
    }

    @Test
    public void selectMode_canSwitchThroughAllModes() {
        HomeViewModel viewModel = new HomeViewModel(Collections.emptyList());

        viewModel.selectMode(HomeMode.TIMED);
        assertEquals(HomeMode.TIMED, viewModel.getUiState().getValue().getSelectedMode());

        viewModel.selectMode(HomeMode.THEME);
        assertEquals(HomeMode.THEME, viewModel.getUiState().getValue().getSelectedMode());

        viewModel.selectMode(HomeMode.CLASSIC);
        assertEquals(HomeMode.CLASSIC, viewModel.getUiState().getValue().getSelectedMode());
    }

    @Test
    public void selectCategory_updatesSelectedCategoryId() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0),
                new HomeCategoryItem("sports", "S", 0, 0)
        ));

        viewModel.selectCategory("sports");

        HomeUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals("sports", state.getSelectedCategoryId());
    }

    @Test
    public void selectCategory_preservesSelectedMode() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0)
        ));
        viewModel.selectMode(HomeMode.TIMED);

        viewModel.selectCategory("nature");

        HomeUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals(HomeMode.TIMED, state.getSelectedMode());
        assertEquals("nature", state.getSelectedCategoryId());
    }

    @Test
    public void selectCategory_categoriesListRemainsUnchanged() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0),
                new HomeCategoryItem("sports", "S", 0, 0)
        ));

        viewModel.selectCategory("sports");

        assertEquals(2, viewModel.getUiState().getValue().getCategories().size());
    }
}
