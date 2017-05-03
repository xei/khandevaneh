package ir.tvnasim.khandevaneh.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ir.tvnasim.khandevaneh.app.App;

/**
 * Created by hamidreza on 4/14/17.
 */

public class SharedPreferencesHelper {

    private static final String KEY_SHARED_PREFERENCES_ACCESS_TOKEN = "acc";
    private static final String KEY_SHARED_PREFERENCES_REFRESH_TOKEN = "ref";

    public static final String KEY_SHARED_PREFERENCES_LANGUAGE = "language";

    public static void storeAccessToken(String accessToken) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public static String retrieveAccessToken() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        return defaultSharedPreferences.getString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_ACCESS_TOKEN, null);
    }

    public static void storeRefreshToken(String refreshToken) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public static String retrieveRefreshToken() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        return defaultSharedPreferences.getString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_REFRESH_TOKEN, null);
    }

    public static void saveLanguageToSharedPreference(Context context, String language) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_LANGUAGE, language);
        editor.commit();
    }

    public static String getLanguageFromSharedPreference() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getApplication());
        String language = defaultSharedPreferences.getString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_LANGUAGE, "fa");
        return language;
    }

}
