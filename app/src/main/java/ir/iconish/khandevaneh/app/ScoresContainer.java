package ir.iconish.khandevaneh.app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/12/17.
 */

public class ScoresContainer {

    @SerializedName("exLevel")
    private String experienceScore;

    @SerializedName("credit")
    private String melonScore;

    @SerializedName("message")
    private String alert;


    public int getExperienceScore() {
        return Integer.parseInt(experienceScore);
    }

    public void setExperienceScore(String experienceScore) {
        this.experienceScore = experienceScore;
    }

    public int getMelonScore() {
        return Integer.parseInt(melonScore);
    }

    public void setMelonScore(String melonScore) {
        this.melonScore = melonScore;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

}
