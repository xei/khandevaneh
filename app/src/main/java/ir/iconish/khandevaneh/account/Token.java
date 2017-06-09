package ir.iconish.khandevaneh.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/3/17.
 */

public class Token {

    @SerializedName("accessToken")
    private String acessToken;

    @SerializedName("refreshToken")
    private String refreshToken;


    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
