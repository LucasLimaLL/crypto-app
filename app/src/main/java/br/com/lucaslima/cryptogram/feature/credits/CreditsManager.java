package br.com.lucaslima.cryptogram.feature.credits;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class CreditsManager {

    private static final String PREFS_NAME = "credits_prefs";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_PUZZLES = "puzzles";
    private static final String KEY_STREAK = "streak";
    private static final String KEY_LAST_DAY = "last_puzzle_day";

    private static CreditsManager instance;
    private final SharedPreferences prefs;

    private CreditsManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized CreditsManager getInstance(Context context) {
        if (instance == null) {
            instance = new CreditsManager(context);
        }
        return instance;
    }

    public int getBalance() {
        return prefs.getInt(KEY_BALANCE, 0);
    }

    public void add(int amount) {
        prefs.edit().putInt(KEY_BALANCE, getBalance() + amount).apply();
    }

    public int getPuzzlesCompleted() {
        return prefs.getInt(KEY_PUZZLES, 0);
    }

    public int getStreak() {
        return prefs.getInt(KEY_STREAK, 0);
    }

    public void recordPuzzleCompleted(int creditsReward) {
        long todayDay = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
        long lastDay = prefs.getLong(KEY_LAST_DAY, -1);

        int newStreak;
        if (lastDay == todayDay) {
            newStreak = getStreak();
        } else if (lastDay == todayDay - 1) {
            newStreak = getStreak() + 1;
        } else {
            newStreak = 1;
        }

        prefs.edit()
                .putInt(KEY_BALANCE, getBalance() + creditsReward)
                .putInt(KEY_PUZZLES, getPuzzlesCompleted() + 1)
                .putInt(KEY_STREAK, newStreak)
                .putLong(KEY_LAST_DAY, todayDay)
                .apply();
    }
}
