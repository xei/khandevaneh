package ir.iconish.khandevaneh.account;

import ir.iconish.khandevaneh.helper.SharedPreferencesHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.app.ScoresContainer;

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
    private String mAvatarEncodedBitmap;
    private String mPhoneNo;
    private String mEmailAddress;
    private String mPostalAddress;

    private boolean mIsProfileComplete;


    private User() {

    }

    public static synchronized User getInstance() {
        if (sUser == null) {
            sUser = new User();
        }
        return sUser;
    }

    public void isLoggedIn(final IsLoggedInListener listener) {
        if (mAccessToken != null && AuthHelper.isTokenValid(mAccessToken)) {
            listener.isLoggedIn(true);
        } else if (mRefreshToken != null) {
            WebApiHelper.authenticateWithRefreshToken(mRefreshToken, "requestTag_User_authWithRefreshToken", new WebApiRequest.WebApiListener<Token>() {
                @Override
                public void onResponse(Token token, ScoresContainer scoresContainer) {
                    User.getInstance().setAccessToken(token.getAcessToken());
                    User.getInstance().setRefreshToken(token.getRefreshToken());
                    listener.isLoggedIn(true);
                }

                @Override
                public void onErrorResponse(String errorMessage) {
                    listener.isLoggedIn(false);
                }
            }, null).send();
        } else {
            listener.isLoggedIn(false);
        }
    }

    public void logout() {
        sUser = new User();
        SharedPreferencesHelper.storeAccessToken(null);
        SharedPreferencesHelper.storeRefreshToken(null);
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            mFirstName = userInfo.getFirstName();
            mLastName = userInfo.getLastName();
            mPhoneNo = userInfo.getPhoneNo();
            mAvatar = userInfo.getAvatar();
            mEmailAddress = userInfo.getEmailAddress();
            mPostalAddress = userInfo.getPostalAddress();
            mMelonScore = userInfo.getMelonScore();
            mExperienceScore = userInfo.getExperienceScore();
            mIsProfileComplete = userInfo.isProfileCompleted();
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

    public String getAvatarEncodedBitmap() {
        return mAvatarEncodedBitmap;
    }

    public void setAvatarEncodedBitmap(String avatarEncodedBitmap) {
        this.mAvatarEncodedBitmap = avatarEncodedBitmap;
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

    public boolean isProfileComplete() {
        return this.mIsProfileComplete;
    }

    public void setIsProfileComplete(boolean isProfileComplete) {
        this.mIsProfileComplete = isProfileComplete;
    }
}
