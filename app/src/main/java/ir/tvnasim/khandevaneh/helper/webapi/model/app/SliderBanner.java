package ir.tvnasim.khandevaneh.helper.webapi.model.app;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by hamidreza on 5/3/17.
 */

public class SliderBanner {

    @SerializedName("id")
    private String id;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("param")
    private HashMap<String, String> metaData;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(HashMap<String, String> metaData) {
        this.metaData = metaData;
    }
}
