package br.com.lucaslima.cryptogram;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class CryptogramApplication extends Application {

    private static final String PREFS_NAME = "criptograma_prefs";
    private static final String KEY_LARGE_MODE = "KEY_LARGE_MODE";
    private static final String KEY_DALTONISM_MODE = "KEY_DALTONISM_MODE";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public boolean isLargeMode() {
        return prefs().getBoolean(KEY_LARGE_MODE, false);
    }

    public boolean isDaltonismMode() {
        return prefs().getBoolean(KEY_DALTONISM_MODE, false);
    }

    public void setLargeMode(boolean enabled) {
        prefs().edit().putBoolean(KEY_LARGE_MODE, enabled).apply();
    }

    public void setDaltonismMode(boolean enabled) {
        prefs().edit().putBoolean(KEY_DALTONISM_MODE, enabled).apply();
    }

    private SharedPreferences prefs() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}
