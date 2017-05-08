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
    private boolean commentSent;


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
        return commentSent;
    }

    public void setCommentSent(boolean commentSent) {
        this.commentSent = commentSent;
    }
}
