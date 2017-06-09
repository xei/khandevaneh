package ir.iconish.khandevaneh.polling;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/11/17.
 */

public class PollingStatisticsItem {

    @SerializedName("content")
    private String value;

    @SerializedName("frequency")
    private String frequency;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getFrequency() {
        return Long.parseLong(frequency);
    }

}
