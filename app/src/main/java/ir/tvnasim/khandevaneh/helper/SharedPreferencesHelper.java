package ir.tvnasim.khandevaneh.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hamidreza on 4/14/17.
 */

public class SharedPreferencesHelper {

    private static final String NAME_SHARED_PREFERENCES_DELIVERY = "PREF_DELIVERY";
    private static final String NAME_SHARED_PREFERENCES_QUESTION = "PREF_QUESTION";

    public static final String KEY_SHARED_PREFERENCES_LANGUAGE = "language";

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getDeliverySharedPreferences(Context context) {
        return context.getSharedPreferences(NAME_SHARED_PREFERENCES_DELIVERY, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getQuestionSharedPreferences(Context context) {
        return context.getSharedPreferences(NAME_SHARED_PREFERENCES_QUESTION, Context.MODE_PRIVATE);
    }

    public static void storeAccessToken(String accessToken) {
    }

    public static String retrieveAccessToken() {
        return "ACCESS-TOKEN";
    }

    public static void storeRefreshToken(String refreshToken) {

    }

    public static String retrieveRefreshToken() {
        return "ACCESS-TOKEN";
    }
}
