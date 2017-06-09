package ir.iconish.khandevaneh.helper.typography;

import android.graphics.Typeface;
import android.util.Log;

import java.util.Locale;

import ir.iconish.khandevaneh.app.App;

/**
 * Created by hamidreza on 2/14/16.
 */
public class TypeFaceHelper {

    private static final String TAG_DEBUG = TypeFaceHelper.class.getSimpleName();

    public static final int FONT_DEFAULT_LOCALE = 1;
    public static final int FONT_ROBOTO_REGULAR = 2;
    public static final int FONT_ROBOTO_BOLD = 3;
    public static final int FONT_IRAN_SANS_REGULAR = 4;
    public static final int FONT_IRAN_SANS_BOLD = 5;

    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_PERSIAN = "fa";

    private static TypeFaceHelper sInstance;

    private Typeface mRobotoRegularTypeface;
    private Typeface mRobotoBoldTypeface;
    private Typeface mIranSansRegularTypeface;
    private Typeface mIranSansBoldTypeface;

    private TypeFaceHelper() {
        // Private constructor prevents instantiation with "new" operator.
    }

    public static synchronized TypeFaceHelper getInstance() {
        if(sInstance == null) {
            sInstance = new TypeFaceHelper();
            sInstance.init();
        }
        return sInstance;
    }

    private void init() {
        mRobotoRegularTypeface = Typeface.createFromAsset(App.getApplication().getAssets(), "fonts/Roboto-Regular.ttf");
        mRobotoBoldTypeface = Typeface.createFromAsset(App.getApplication().getAssets(), "fonts/Roboto-Bold.ttf");
        mIranSansRegularTypeface = Typeface.createFromAsset(App.getApplication().getAssets(), "fonts/IRANSansMobile.ttf");
        mIranSansBoldTypeface = Typeface.createFromAsset(App.getApplication().getAssets(), "fonts/IRANSansMobile_Bold.ttf");
    }

    public Typeface getTypeFace(int font){
        switch (font) {
            case FONT_DEFAULT_LOCALE:
                return getDefaultFontBasedOnLocale();
            case FONT_ROBOTO_REGULAR:
                return mRobotoRegularTypeface;

            case FONT_ROBOTO_BOLD:
                return mRobotoBoldTypeface;

            case FONT_IRAN_SANS_REGULAR:
                return mIranSansRegularTypeface;

            case FONT_IRAN_SANS_BOLD:
                return mIranSansBoldTypeface;

            default:
                Log.e(TAG_DEBUG, "Can not get typeface: Invalid Font!");
                return null;
        }
    }

    private Typeface getDefaultFontBasedOnLocale() {
        String language = Locale.getDefault().getLanguage();
        if(language.equals(LANGUAGE_ENGLISH)) {
            return mRobotoRegularTypeface;
        } else if(language.equals(LANGUAGE_PERSIAN)) {
            return mIranSansRegularTypeface;
        } else {
            // Using default typeface for other languages.
            return null;
        }
    }

}
