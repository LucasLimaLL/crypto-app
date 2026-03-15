package br.com.lucaslima.cryptogram;

import android.app.Application;
import android.util.Log;

/**
 * Application class for the Cryptogram app.
 *
 * <p>Serves as the entry point for global initialization, such as dependency injection
 * frameworks, analytics, and crash reporting. Add only what is strictly necessary here;
 * heavyweight initialization should be deferred to avoid slow app startups.
 */
public class CryptogramApplication extends Application {

    private static final String TAG = "CryptogramApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application created");
    }
}
