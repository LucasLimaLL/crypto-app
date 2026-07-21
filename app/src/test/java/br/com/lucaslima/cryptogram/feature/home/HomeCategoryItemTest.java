package br.com.lucaslima.cryptogram.feature.home;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.home.ui.HomeCategoryItem;

public class HomeCategoryItemTest {

    @Test
    public void getId_returnsConstructorValue() {
        HomeCategoryItem item = new HomeCategoryItem("nature", "🌿", 1, 2);
        assertEquals("nature", item.getId());
    }

    @Test
    public void getIconGlyph_returnsConstructorValue() {
        HomeCategoryItem item = new HomeCategoryItem("sports", "⚽", 10, 20);
        assertEquals("⚽", item.getIconGlyph());
    }

    @Test
    public void getTitleRes_returnsConstructorValue() {
        HomeCategoryItem item = new HomeCategoryItem("food", "🍽", 42, 99);
        assertEquals(42, item.getTitleRes());
    }

    @Test
    public void getSubtitleRes_returnsConstructorValue() {
        HomeCategoryItem item = new HomeCategoryItem("science", "🔬", 1, 77);
        assertEquals(77, item.getSubtitleRes());
    }

    @Test
    public void allFields_setAndRetrievedIndependently() {
        HomeCategoryItem item = new HomeCategoryItem("id-x", "★", 100, 200);
        assertEquals("id-x", item.getId());
        assertEquals("★", item.getIconGlyph());
        assertEquals(100, item.getTitleRes());
        assertEquals(200, item.getSubtitleRes());
    }
}
