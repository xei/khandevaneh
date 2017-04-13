package ir.tvnasim.khandevaneh.account;

/**
 * Created by hamidreza on 4/14/17.
 */

public class User {

    private static User sUser;

    private String mAccessToken;
    private String mRefreshToken;


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
}
