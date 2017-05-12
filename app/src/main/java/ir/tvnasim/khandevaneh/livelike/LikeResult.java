package ir.tvnasim.khandevaneh.livelike;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/7/17.
 * All rights reserved by Digikala.
 */

public class LikeResult {

    @SerializedName("exLevel")
    private int userExperience;

    public int getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }
}
