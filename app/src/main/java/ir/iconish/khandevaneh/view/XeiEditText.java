package ir.iconish.khandevaneh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.util.Locale;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.typography.TypeFaceHelper;

/**
 * Created by hamidreza on 6/23/16.
 */
public class XeiEditText extends AppCompatEditText {

    private static final int LOCALE_AUTO = 0;
    private static final int LOCALE_EN_US = 1;
    private static final int LOCALE_FA_IR = 2;
    private static final int LOCALE_HYBRID = 3;

    private static final int STYLE_NORMAL = 0;
    private static final int STYLE_BOLD = 1;

    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_PERSIAN = "fa";


    public XeiEditText(Context context) {
        super(context);
        init(context, null);
    }

    public XeiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XeiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs != null) {
            setLocaleTypeface(context, attrs);
        }
    }

    private void setLocaleTypeface(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XeiLocaledView);

        try {
            int localeEnum = typedArray.getInt(R.styleable.XeiLocaledView_locale, LOCALE_AUTO);
            int styleFlag = typedArray.getInt(R.styleable.XeiLocaledView_style, STYLE_NORMAL);
            TypeFaceHelper typeFaceHelper = TypeFaceHelper.getInstance();

            switch (localeEnum) {
                case LOCALE_AUTO:
                    String language = Locale.getDefault().getLanguage();
                    if(language.equals(LANGUAGE_ENGLISH)) {
                        switch (styleFlag) {
                            case STYLE_NORMAL:
                                setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_ROBOTO_REGULAR));
                                break;
                            case STYLE_BOLD:
                                setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_ROBOTO_BOLD));
                                break;
                        }
                    } else if(language.equals(LANGUAGE_PERSIAN)) {
                        switch (styleFlag) {
                            case STYLE_NORMAL:
                                setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_IRAN_SANS_REGULAR));
                                break;
                            case STYLE_BOLD:
                                setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_IRAN_SANS_BOLD));
                                break;
                        }
                    } else {
                        // Using default typeface for other languages.
                    }
                    break;
                case LOCALE_EN_US:
                    switch (styleFlag) {
                        case STYLE_NORMAL:
                            setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_ROBOTO_REGULAR));
                            break;
                        case STYLE_BOLD:
                            setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_ROBOTO_BOLD));
                            break;
                    }
                    break;
                case LOCALE_FA_IR:
                    switch (styleFlag) {
                        case STYLE_NORMAL:
                            setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_IRAN_SANS_REGULAR));
                            break;
                        case STYLE_BOLD:
                            setTypeface(typeFaceHelper.getTypeFace(TypeFaceHelper.FONT_IRAN_SANS_BOLD));
                            break;
                    }
                    break;
                case LOCALE_HYBRID:
                    // TODO: handle this with Android Data Binding Support Library.
                    break;
            }
        } catch (Exception e) {

        } finally {
            typedArray.recycle();
        }

    }
}
