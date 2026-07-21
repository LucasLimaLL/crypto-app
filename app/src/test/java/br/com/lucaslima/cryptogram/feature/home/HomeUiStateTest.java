package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.lucaslima.cryptogram.feature.home.ui.HomeCategoryItem;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeMode;
import br.com.lucaslima.cryptogram.feature.home.ui.HomeUiState;

public class HomeUiStateTest {

    @Test
    public void getSelectedMode_returnsConstructorValue() {
        HomeUiState state = new HomeUiState(HomeMode.TIMED, Collections.emptyList(), null);
        assertEquals(HomeMode.TIMED, state.getSelectedMode());
    }

    @Test
    public void getSelectedCategoryId_returnsConstructorValue() {
        HomeUiState state = new HomeUiState(HomeMode.CLASSIC, Collections.emptyList(), "sports");
        assertEquals("sports", state.getSelectedCategoryId());
    }

    @Test
    public void getSelectedCategoryId_nullIsAllowed() {
        HomeUiState state = new HomeUiState(HomeMode.THEME, Collections.emptyList(), null);
        assertNull(state.getSelectedCategoryId());
    }

    @Test
    public void getCategories_returnsAllItems() {
        List<HomeCategoryItem> items = Arrays.asList(
                new HomeCategoryItem("a", "A", 0, 0),
                new HomeCategoryItem("b", "B", 0, 0)
        );
        HomeUiState state = new HomeUiState(HomeMode.CLASSIC, items, "a");
        assertEquals(2, state.getCategories().size());
    }

    @Test
    public void getCategories_preservesOrder() {
        List<HomeCategoryItem> items = Arrays.asList(
                new HomeCategoryItem("first", "F", 0, 0),
                new HomeCategoryItem("second", "S", 0, 0)
        );
        HomeUiState state = new HomeUiState(HomeMode.CLASSIC, items, "first");
        assertEquals("first", state.getCategories().get(0).getId());
        assertEquals("second", state.getCategories().get(1).getId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCategories_listIsUnmodifiable() {
        List<HomeCategoryItem> items = Arrays.asList(new HomeCategoryItem("a", "A", 0, 0));
        HomeUiState state = new HomeUiState(HomeMode.CLASSIC, items, "a");
        state.getCategories().add(new HomeCategoryItem("b", "B", 0, 0));
    }

    @Test
    public void emptyCategories_isAllowed() {
        HomeUiState state = new HomeUiState(HomeMode.THEME, Collections.emptyList(), null);
        assertEquals(0, state.getCategories().size());
    }
}
