package ir.tvnasim.khandevaneh.helper.webapi.model.store;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/8/17.
 * All rights reserved by Digikala.
 */

public class StoreItem {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private String price;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
