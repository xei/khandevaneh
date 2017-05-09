package ir.tvnasim.khandevaneh.helper.webapi.model.section;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/9/17.
 */

public class SectionContainer {

    @SerializedName("item")
    private Section section;

    @SerializedName("serverDate")
    private String serverTime;


    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

}
