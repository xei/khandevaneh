package ir.tvnasim.khandevaneh.archive;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/20/17.
 */

public class ArchiveItem {

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

    @SerializedName("userTotalComment")
    private String userTotalComment;

    @SerializedName("isOpen")
    private String isOpen;

    @SerializedName("needCredit")
    private String needCredit;

    @SerializedName("content")
    private String content;


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

    public String getUserTotalComment() {
        return userTotalComment;
    }

    public void setUserTotalComment(String userTotalComment) {
        this.userTotalComment = userTotalComment;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getNeedCredit() {
        return needCredit;
    }

    public void setNeedCredit(String needCredit) {
        this.needCredit = needCredit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
