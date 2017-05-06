package ir.tvnasim.khandevaneh.polling;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/3/17.
 */

class PollingOption {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String title;

    @SerializedName("imageUrl")
    private String imageUrl;


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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
