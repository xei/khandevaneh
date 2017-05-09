package ir.tvnasim.khandevaneh.polling;

/**
 * Created by hamidreza on 5/3/17.
 */

public class OptionType {

    public static final int TYPE_OPTION_SINGLE = 1;
    public static final int TYPE_OPTION_MULTI = 2;

    int mType;

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
