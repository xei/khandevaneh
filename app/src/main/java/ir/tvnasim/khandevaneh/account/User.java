package ir.tvnasim.khandevaneh.account;

/**
 * Created by hamidreza on 4/14/17.
 */

public class User {

    private static final int SCORE_NOT_SET = -1;

    private static User sUser;

    private String mAccessToken;
    private String mRefreshToken;

    private int mMelonScore = SCORE_NOT_SET;
    private int mExperienceScore = SCORE_NOT_SET;


    private User() {

    }

    public static synchronized User getInstance() {
        if (sUser == null) {
            sUser = new User();
        }
        return sUser;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.mRefreshToken = refreshToken;
    }

    public int getMelonScore() {
        return mMelonScore;
    }

    public void setMelonScore(int melonScore) {
        this.mMelonScore = melonScore;
    }

    public int getExperienceScore() {
        return mExperienceScore;
    }

    public void setExperienceScore(int experienceScore) {
        this.mExperienceScore = experienceScore;
    }
}
