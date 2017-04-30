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

    private String mFirstName;
    private String mLastName;
    private String mAvatar;
    private String mPhoneNo;
    private String mEmailAddress;
    private String mPostalAddress;



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

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
    }

    public String getPhoneNo() {
        return mPhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.mPhoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
    }

    public String getPostalAddress() {
        return mPostalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.mPostalAddress = postalAddress;
    }

    public interface IsLoggedInListener {
        void isLoggedIn(boolean isLoggedIn);
    }

}
