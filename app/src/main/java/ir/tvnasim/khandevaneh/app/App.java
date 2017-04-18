package ir.tvnasim.khandevaneh.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.imageloading.ImageLoader;
import ir.tvnasim.khandevaneh.helper.webapi.VolleyHelper;
import okhttp3.internal.Util;

/**
 * Created by hamidreza on 4/14/17.
 */

public class App extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        VolleyHelper.init(this);
        ImageLoader.init(this);
//        setPreferenceLocale();


    }

    public static synchronized Context getApplication() {
        return mInstance;
    }

    /**
     * This method sets the application locale using the specified language.
     *
     * @param language determine the application language an can be like "fa" or 'en' or ...
     */
    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
    }

    private void setPreferenceLocale() {
        String appLanguage = HelperFunctions.getLanguageFromSharedPreference(this);
        setLocale(appLanguage);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        setPreferenceLocale();
    }

}
