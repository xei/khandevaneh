package ir.tvnasim.khandevaneh.helper.webapi;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.App;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.SliderBanner;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.StartupConfig;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.Token;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.UserInfo;
import ir.tvnasim.khandevaneh.leaderboard.LeaderViewModel;

/**
 * @author H. Hosseinkhani
 *         <p>
 *         All rights reserved for Digikala corporation.
 */
public final class WebApiHelper {

    public static final String ENDPOINT_LOGIN = "xxx";


    public static final String ENDPOINT_GET_STARTUP = "app/getStartup";
    public static final String ENDPOINT_GET_STARTUP_CONFIG = "app/getConfig";
    public static final String ENDPOINT_GET_SLIDER_BANNERS = "app/getSlider";
    public static final String ENDPOINT_GET_BANNER = "app/getBanner";

    public static final String ENDPOINT_REGISTER_PHONE_NO = "user/registration";
    public static final String ENDPOINT_AUTH_WITH_CREDENTIALS= "user/activation";
    public static final String ENDPOINT_AUTH_WITH_REFRESH_TOKEN = "user/auth";
    public static final String ENDPOINT_GET_USER_INFO = "user/info";
    public static final String ENDPOINT_EDIT_USER_INFO = "user/edit";
    public static final String ENDPOINT_GET_LEADERBOARD = "user/leaderboard";


    public static WebApiRequest<StartupConfig> getStartupConfig(String requestTag, WebApiRequest.WebApiListener<StartupConfig> webApiListener, WebApiRequest.LoadRequests fragment) {

        String appType = "ANDROID";
        int appVersionCode = HelperFunctions.getAppVersionCode(App.getApplication());

        HashMap<String, String> params = new HashMap<>();
        params.put("", appType);
        params.put("", String.valueOf(appVersionCode));

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_STARTUP_CONFIG,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<StartupConfig>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<SliderBanner>> getSliderBanners(String requestTag, WebApiRequest.WebApiListener<ArrayList<SliderBanner>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_SLIDER_BANNERS,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<SliderBanner>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<Object> registerPhoneNo(String phoneNo, String requestTag, WebApiRequest.WebApiListener<Object> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("username", phoneNo);

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_REGISTER_PHONE_NO,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<Object>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<Token> authenticateWithCredentials(String phoneNo, String verificationCode, String requestTag, WebApiRequest.WebApiListener<Token> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("username", phoneNo);
        params.put("activeCode", verificationCode);

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_AUTH_WITH_CREDENTIALS,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<Token>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<Token> authenticateWithRefreshToken(String refreshToken, String requestTag, WebApiRequest.WebApiListener<Token> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("refreshToken", refreshToken);

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_AUTH_WITH_REFRESH_TOKEN,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<Token>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<UserInfo> getUserInfo(String requestTag, WebApiRequest.WebApiListener<UserInfo> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_USER_INFO,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<UserInfo>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<Boolean> editUserInfo(User user, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("email", user.getEmailAddress());
        params.put("address", user.getPostalAddress());
        params.put("profilePicFile", user.getAvatar());
        params.put("profilePicType", "1");

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_EDIT_USER_INFO,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<LeaderViewModel>> getLeaderBoard(String requestTag, WebApiRequest.WebApiListener<ArrayList<LeaderViewModel>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_LEADERBOARD,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<LeaderViewModel>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

}