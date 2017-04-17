package ir.tvnasim.khandevaneh.helper;

import android.util.Log;

/**
 * Created by hamidreza on 4/14/17.
 */

public class LogHelper {

    public static void logError(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void logInfo(String tag, String msg) {
        Log.i(tag, msg);
    }

}
