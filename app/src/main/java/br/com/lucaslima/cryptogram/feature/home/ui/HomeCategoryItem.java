package br.com.lucaslima.cryptogram.feature.home.ui;

import androidx.annotation.StringRes;

public final class HomeCategoryItem {

    private final String id;
    private final String iconGlyph;
    private final int titleRes;
    private final int subtitleRes;

    public HomeCategoryItem(String id, String iconGlyph, @StringRes int titleRes, @StringRes int subtitleRes) {
        this.id = id;
        this.iconGlyph = iconGlyph;
        this.titleRes = titleRes;
        this.subtitleRes = subtitleRes;
    }

    public String getId() {
        return id;
    }

    public String getIconGlyph() {
        return iconGlyph;
    }

    @StringRes
    public int getTitleRes() {
        return titleRes;
    }

    @StringRes
    public int getSubtitleRes() {
        return subtitleRes;
    }
}
