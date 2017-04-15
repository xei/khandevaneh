package ir.tvnasim.khandevaneh.account;

/**
 * Created by hamidreza on 4/14/17.
 */

public class User {

    private static final int SCORE_NOT_SET = -1;

    private static User sUser;

    private String mAccessToken;
    private String mRefreshToken;

    private String mName;
    private String mAvatar;

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

    public void isLoggedIn(IsLoggedInListener listener) {
        if (mAccessToken != null && AuthHelper.isTokenValid(mAccessToken)) {
            listener.isLoggedIn(true);
        } else if (mRefreshToken != null) {
            // TODO: make a request to auth api with grant_type=refresh_token and call listener
        } else {
            listener.isLoggedIn(false);
        }
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
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

    public interface IsLoggedInListener {
        void isLoggedIn(boolean isLoggedIn);
    }
}
