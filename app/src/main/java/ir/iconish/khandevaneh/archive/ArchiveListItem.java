package ir.iconish.khandevaneh.archive;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/20/17.
 */

public class ArchiveListItem {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("type")
    private String type;

    @SerializedName("archiveCategoryId")
    private String categoryId;

    @SerializedName("likeCount")
    private String likeCount;

    @SerializedName("isOpen")
    private String isAccessible;

    @SerializedName("needCredit")
    private String payableMelon;


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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isAccessible() {
        return isAccessible.equals("1");
    }

    public void setIsAccessible(String isAccessible) {
        this.isAccessible = isAccessible;
    }

    public long getPayableMelon() {
        return Long.parseLong(payableMelon);
    }

    public void setPayableMelon(String payableMelon) {
        this.payableMelon = payableMelon;
    }
}
