package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

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
    public void selectCategory_keepsNewCategoryIdInState() {
        HomeViewModel viewModel = new HomeViewModel(Arrays.asList(
                new HomeCategoryItem("nature", "N", 0, 0),
                new HomeCategoryItem("sports", "S", 0, 0)
        ));

        viewModel.selectCategory("sports");

        HomeUiState state = viewModel.getUiState().getValue();
        assertNotNull(state);
        assertEquals("sports", state.getSelectedCategoryId());
    }
}
