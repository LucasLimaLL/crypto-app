package br.com.lucaslima.cryptogram;

import android.app.Application;
import android.util.Log;

public class CryptogramApplication extends Application {

    private static final String TAG = "CryptogramApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application created");
    }
}
