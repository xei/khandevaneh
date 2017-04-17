package ir.tvnasim.khandevaneh.helper.typography;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

/**
 * @author Hamidreza Hosseinkhani
 *		   hosseinkhani@live.com
 *
 * This class helps you to set more that one typeface to a TextView.
 * By default, I declared two syntax (English & Arabic) and set the fonts to TextView based on the syntax.
 * Developers must instantiate this class by passing the fonts path from "Assets" directory and call "performTypeface" method.
 * Notice that this method is expensive and may take some mili-seconds to appear the text in the TextView, since performing a task on the text,
 * must posted to the end of the View looper queue.
 * This class is using "Calligraphy" library, then import it ASAP. (https://github.com/chrisjenx/Calligraphy)
 * 
 * You can add or modify scripts by overriding the method "getSyntax".
 * 
 */
public class MultiTypefaceHelper {
	private final static int SYNTAX_ENGLISH = 0;
	private final static int SYNTAX_ARABIC = 1;
	
	private Typeface mEnglishTypeface;
	private Typeface mArabicTypeface;
	
	public MultiTypefaceHelper(Context context, String englishFontPath, String arabicFontPath) {
		this.mEnglishTypeface = TypefaceUtils.load(context.getAssets(), englishFontPath);
		this.mArabicTypeface = TypefaceUtils.load(context.getAssets(), arabicFontPath);
	}
	
	public void performTypeface(TextView tv, String text, boolean forceRtl) {
		if(tv == null || text == null || text.length() == 0) {
			return;
		}
		if(forceRtl) {
			text = '\u200f' + text;
			text = text.replace("\r\n", "\r\n\u200f");
		}
		SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
		int startIndex = 0;
		int index;
		int currentSyntax = getSyntax(text.codePointAt(startIndex));
		for(index = 1 ; index < text.length() ; index++) {
			int tmpSyntax = getSyntax(text.codePointAt(index));
			if(tmpSyntax != currentSyntax) {
				setTypeface(stringBuilder, startIndex, index, currentSyntax);
				currentSyntax = tmpSyntax;
				startIndex = index;
			}
		}
		setTypeface(stringBuilder, startIndex, index, currentSyntax);
		tv.setText(stringBuilder);
	}
	
	/**
	 * This method set the typefaces to the text on the TextView.
	 * This may take a few mili-seconds
	 * This traverse a string and use an Automata to build a formatted word.
	 * 
	 * @param tv is the TextView
	 * @param text is the formated text
	 */
	public void performTypeface(TextView tv, String text) {
		performTypeface(tv, text, true);
	}
	
	private void setTypeface(SpannableStringBuilder stringBuilder, int start, int end, int syntax) {
		switch (syntax) {
		case SYNTAX_ENGLISH:
			stringBuilder.setSpan(new CalligraphyTypefaceSpan(mEnglishTypeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			break;
			
		case SYNTAX_ARABIC:
			stringBuilder.setSpan(new CalligraphyTypefaceSpan(mArabicTypeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			break;

		default:
			stringBuilder.setSpan(new CalligraphyTypefaceSpan(mEnglishTypeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			break;
		}
	}
	
	/**
	 * This method is a helper function to determine the syntax of a character based on the Unicode.
	 * 
	 * @param unicode : the Unicode of a character
	 * @return determined syntax of that character
	 */
	private int getSyntax(int unicode) {
		return (unicode >= 0x0600 && unicode <= 0x06ff) ? SYNTAX_ARABIC : SYNTAX_ENGLISH;
	}
}
