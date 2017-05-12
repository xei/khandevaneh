package ir.tvnasim.khandevaneh.app;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by hamidreza on 5/3/17.
 */

public class Banner {

    public static final String LOCATION_COMPETITION_LIST = "2";
    public static final String LOCATION_COMPETITION = "3";
    public static final String LOCATION_COMPETITION_STATISTICS = "4";
    public static final String LOCATION_LIVE_LIKE = "1";
    public static final String LOCATION_POLLING_LIST = "5";
    public static final String LOCATION_POLLING = "6";
    public static final String LOCATION_POLLING_STATISTICS = "7";
    public static final String LOCATION_STORE = "9";
    public static final String LOCATION_LEADER_BOARD = "";
    public static final String LOCATION_CAMPAIGN = "";
    public static final String LOCATION_ARCHIVE = "";
    public static final String LOCATION_AWARDS = "";
    public static final String LOCATION_PROFILE = "8";

    @SerializedName("id")
    private String id;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("destination")
    private String destination;

    @SerializedName("param")
    private String destinationParam;

    @SerializedName("location")
    private String location;


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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationParam() {
        return destinationParam;
    }

    public void setDestinationParam(String destinationParam) {
        this.destinationParam = destinationParam;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
