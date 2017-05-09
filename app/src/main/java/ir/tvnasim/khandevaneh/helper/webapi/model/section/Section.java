package ir.tvnasim.khandevaneh.helper.webapi.model.section;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/7/17.
 * All rights reserved by Digikala.
 */

public class Section {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("timescope")
    private String timeScope;

    @SerializedName("sendComment")
    private int commentSent;


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

    public String getTimeScope() {
        return timeScope;
    }

    public void setTimeScope(String timeScope) {
        this.timeScope = timeScope;
    }

    public boolean isCommentSent() {
        if (commentSent == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setCommentSent(int commentSent) {
        this.commentSent = commentSent;
    }
}
