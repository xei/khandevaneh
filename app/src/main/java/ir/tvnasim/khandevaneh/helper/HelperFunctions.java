package ir.tvnasim.khandevaneh.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hamidreza on 12/22/16.
 */

public class HelperFunctions {

    private static final String TAG_DEBUG = HelperFunctions.class.getSimpleName();

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

//    public static void showRtlSnackbar(View parent, String alertText, String actionText, int actionColor, View.OnClickListener actionClickListener) {
//        Snackbar snackbar = Snackbar.make(parent, alertText, Snackbar.LENGTH_INDEFINITE);
//        View snackbarView = snackbar.getView();
////         snackbarView.setBackgroundColor(Color.parseColor("#f5363f"));
//        TextView alertTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//        TextView actionTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_action);
////        alertTextView.setTypeface(TypeFaceHelper.getTypeFace(TypeFaceHelper.IRAN_SANS));
////        actionTextView.setTypeface(TypeFaceHelper.getTypeFace(TypeFaceHelper.IRAN_SANS));
//        ((LinearLayout) snackbarView).removeView(alertTextView);
//        ((LinearLayout) snackbarView).addView(alertTextView);
//        alertTextView.setTextColor(Color.WHITE);
//        snackbar.setActionTextColor(ContextCompat.getColor(parent.getContext(), actionColor));
//        snackbar.setAction(actionText, actionClickListener);
////		   snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
//        snackbar.show();
//    }

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG_DEBUG, "Package name not found", e);
            return 0;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG_DEBUG, "Package name not found", e);
            return null;
        }
    }

    public static String formatPrice(String price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingSize(3);

        return df.format(Long.parseLong(price));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    /**
     * This method converts a number string to persian equivalent.
     *
     * @param numberStr The number string that must convert to Persian.
     * @return Persian number string
     * @throws NumberFormatException occurs when the "numberStr" is invalid.
     */
    public static String convertNumberStringToPersian(String numberStr) throws NumberFormatException {
        String persianNumberStr = "";
        for (int i = 0; i < numberStr.length(); i++) {
            int numberUnicode = numberStr.charAt(i);
            int persianUnicode;
            if (numberUnicode >= 48 && numberUnicode <= 57) {
                // The digit character is Latin
                persianUnicode = numberUnicode + 1728;
            } else if (numberUnicode >= 1632 && numberUnicode <= 1641) {
                // The digit is Arabic
                persianUnicode = numberUnicode + 144;
            } else if (numberUnicode >= 1776 && numberUnicode <= 1785) {
                // The digit character is Persian
                persianUnicode = numberUnicode;
            } else {
                // The digit character is invalid
                throw new NumberFormatException("\"numberStr\" has an invalid digit character");
            }
            persianNumberStr += (char) persianUnicode;
        }
        return persianNumberStr;
    }

    /**
     * This method gets a string and converts all digits in it to Persian equivalent.
     *
     * @param str the string that may have some digit characters.
     * @return the processed string that don't have any non-persian digit character.
     */
    public static String persianizeDigitsInString(String str) {
        String persianizedStr = "";
        for (int i = 0; i < str.length(); i++) {
            int unicode = str.charAt(i);
            int persianizedUnicode;
            if (unicode >= 48 && unicode <= 57) {
                // The character is Latin digit
                persianizedUnicode = unicode + 1728;
            } else if (unicode >= 1632 && unicode <= 1641) {
                // The character is Arabic digit
                persianizedUnicode = unicode + 144;
            } else {
                persianizedUnicode = unicode;
            }
            persianizedStr += (char) persianizedUnicode;
        }
        return persianizedStr;
    }

    public static void call(Context context, String tel) throws Exception {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phone = tel.replace("-", "");
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public static void saveLanguageToSharedPreference(Context context, String language) {
        SharedPreferences preferences = SharedPreferencesHelper.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_LANGUAGE, language);
        editor.commit();
    }

    public static String getLanguageFromSharedPreference(Context context) {
        SharedPreferences preferences = SharedPreferencesHelper.getDefaultSharedPreferences(context);
        String language = preferences.getString(SharedPreferencesHelper.KEY_SHARED_PREFERENCES_LANGUAGE, "fa");
        return language;
    }

}
