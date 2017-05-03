package ir.tvnasim.khandevaneh.polling;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hamidreza on 5/3/17.
 */

public class PollingItem {

    public static final int TYPE_POLLING_IMAGE = 0;
    public static final int TYPE_POLLING_TEXT = 1;
    public static final int TYPE_POLLING_VIDEO = 2;
    public static final int TYPE_POLLING_VOICE = 3;

    public static final int TYPE_OPTION_SINGLE = 0;
    public static final int TYPE_OPTION_MULTI = 1;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("pollingType")
    private int pollingType;

    @SerializedName("payload")
    private String payload;

    @SerializedName("optionType")
    private int optionType;

    @SerializedName("options")
    private ArrayList<PollingOption> options;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPollingType() {
        return pollingType;
    }

    public void setPollingType(int pollingType) {
        this.pollingType = pollingType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public ArrayList<PollingOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<PollingOption> options) {
        this.options = options;
    }

}
