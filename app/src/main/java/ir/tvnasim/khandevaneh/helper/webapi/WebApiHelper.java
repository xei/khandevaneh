package ir.tvnasim.khandevaneh.helper.webapi;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.App;
import ir.tvnasim.khandevaneh.archive.ArchiveCategory;
import ir.tvnasim.khandevaneh.archive.ArchiveItem;
import ir.tvnasim.khandevaneh.archive.ArchiveListItem;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.polling.PoleResult;
import ir.tvnasim.khandevaneh.app.Banner;
import ir.tvnasim.khandevaneh.app.StartupConfig;
import ir.tvnasim.khandevaneh.livelike.LikeResult;
import ir.tvnasim.khandevaneh.livelike.SectionContainer;
import ir.tvnasim.khandevaneh.store.StoreItem;
import ir.tvnasim.khandevaneh.account.Token;
import ir.tvnasim.khandevaneh.account.UserInfo;
import ir.tvnasim.khandevaneh.leaderboard.LeaderContainerModel;
import ir.tvnasim.khandevaneh.polling.PollingItem;
import ir.tvnasim.khandevaneh.polling.PollingListItem;
import ir.tvnasim.khandevaneh.polling.PollingStatisticsItem;

/**
 * @author H. Hosseinkhani
 *         <p>
 *         All rights reserved for Digikala corporation.
 */
public final class WebApiHelper {

    static final String ENDPOINT_LOGIN = "xxx";


    public static final String ENDPOINT_GET_STARTUP = "app/getStartup";
    private static final String ENDPOINT_GET_STARTUP_CONFIG = "app/getConfig";
    private static final String ENDPOINT_GET_SLIDER_BANNERS = "app/getSlider";
    public static final String ENDPOINT_GET_BANNER = "app/getBanner";

    private static final String ENDPOINT_REGISTER_PHONE_NO = "user/registration";
    private static final String ENDPOINT_AUTH_WITH_CREDENTIALS= "user/activation";
    private static final String ENDPOINT_AUTH_WITH_REFRESH_TOKEN = "user/auth";
    private static final String ENDPOINT_GET_USER_INFO = "user/info";
    private static final String ENDPOINT_EDIT_USER_INFO = "user/edit";
    private static final String ENDPOINT_GET_LEADERBOARD = "user/leaderboard";
    private static final String ENDPOINT_SEND_PARTICIPATE_REQUEST = "user/registrationToKhandevane";

    private static final String ENDPOINT_GET_LIVE_LIKE = "section/getList";
    private static final String ENDPOINT_LIKE_SECTION = "section/like";
    private static final String ENDPOINT_COMMENT_SECTION = "section/comment";

    private static final String ENDPOINT_GET_POLLING_LIST = "poll/getList";
    private static final String ENDPOINT_GET_POLLING_ITEM = "poll/getItem";
    private static final String ENDPOINT_POLL = "poll/like";
    private static final String ENDPOINT_GET_POLLING_STATISTICS = "poll/getStatistics";

    private static final String ENDPOINT_GET_ARCHIVE_FILTERS_AND_SORTINGS = "archive/getSortAndFilter";
    private static final String ENDPOINT_GET_ARCHIVE_CATEGORIES = "archive/getCategory";
    private static final String ENDPOINT_GET_ARCHIVE_LIST = "archive/getList";
    private static final String ENDPOINT_GET_ARCHIVE_ITEM = "archive/getItem";
    private static final String ENDPOINT_LIKE_ARCHIVE = "archive/like";
    private static final String ENDPOINT_COMMENT_ARCHIVE = "archive/comment";

    private static final String ENDPOINT_GET_STORE_LIST = "store/getList";


    public static WebApiRequest<StartupConfig> getStartupConfig(String requestTag, WebApiRequest.WebApiListener<StartupConfig> webApiListener, WebApiRequest.LoadRequests fragment) {

        String appType = "android";
        int appVersionCode = HelperFunctions.getAppVersionCode(App.getApplication());

        HashMap<String, String> params = new HashMap<>();
        params.put("appType", appType);
        params.put("appVersion", String.valueOf(appVersionCode));

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_GET_STARTUP_CONFIG,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<StartupConfig>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<Banner>> getSliderBanners(String requestTag, WebApiRequest.WebApiListener<ArrayList<Banner>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_SLIDER_BANNERS,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<Banner>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<Banner>> getBanners(String requestTag, WebApiRequest.WebApiListener<ArrayList<Banner>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_BANNER,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<Banner>>>() {
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
        params.put("profilePicFile", user.getAvatarEncodedBitmap());
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

    public static WebApiRequest<LeaderContainerModel> getLeaderBoard(String requestTag, WebApiRequest.WebApiListener<LeaderContainerModel> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_LEADERBOARD,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<LeaderContainerModel>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<Boolean> sendParticipateRequest(String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_SEND_PARTICIPATE_REQUEST,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<PollingListItem>> getPollingList(int type, String requestTag, WebApiRequest.WebApiListener<ArrayList<PollingListItem>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_POLLING_LIST + "?pollType=" + type,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<PollingListItem>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<PollingItem> getPollingItem(String id, String requestTag, WebApiRequest.WebApiListener<PollingItem> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_POLLING_ITEM + "?id=" + id,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<PollingItem>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<PoleResult> poll(String pollingId, ArrayList<String> options, String requestTag, WebApiRequest.WebApiListener<PoleResult> webApiListener, WebApiRequest.LoadRequests fragment) {

        StringBuilder answersParamBuilder = new StringBuilder();
        answersParamBuilder.append(options.get(0));
        for (int i = 1 ; i < options.size() ; i++) {
            answersParamBuilder.append(',');
            answersParamBuilder.append(options.get(i));
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", pollingId);
        params.put("answer", answersParamBuilder.toString());

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_POLL,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<PoleResult>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<PollingStatisticsItem>> getPollingStatistics(String pollingId, String requestTag, WebApiRequest.WebApiListener<ArrayList<PollingStatisticsItem>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_POLLING_STATISTICS + "?id=" + pollingId,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<PollingStatisticsItem>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<ArchiveCategory>> getArchiveCategories(String requestTag, WebApiRequest.WebApiListener<ArrayList<ArchiveCategory>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_ARCHIVE_CATEGORIES,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<ArchiveCategory>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<ArchiveListItem>> getArchiveList(String categoryId, String requestTag, WebApiRequest.WebApiListener<ArrayList<ArchiveListItem>> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_ARCHIVE_LIST,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<ArchiveListItem>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArchiveItem> getArchiveItem(String id, String requestTag, WebApiRequest.WebApiListener<ArchiveItem> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_ARCHIVE_ITEM + "?id=" + id,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArchiveItem>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<SectionContainer> getLiveLike(String requestTag, WebApiRequest.WebApiListener<SectionContainer> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_LIVE_LIKE,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<SectionContainer>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<LikeResult> likeSection(String sectionId, String requestTag, WebApiRequest.WebApiListener<LikeResult> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", sectionId);

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_LIKE_SECTION,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<LikeResult>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<LikeResult> commentOnSection(String sectionId, String msg, String requestTag, WebApiRequest.WebApiListener<LikeResult> webApiListener, WebApiRequest.LoadRequests fragment) {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", sectionId);
        params.put("comment", msg);

        return new WebApiRequest<>(
                Request.Method.POST,
                ENDPOINT_COMMENT_SECTION,
                params,
                new TypeToken<WebApiRequest.WebApiResponse<LikeResult>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

    public static WebApiRequest<ArrayList<StoreItem>> getStoreList(String requestTag, WebApiRequest.WebApiListener<ArrayList<StoreItem>> webApiListener, WebApiRequest.LoadRequests fragment) {

        return new WebApiRequest<>(
                Request.Method.GET,
                ENDPOINT_GET_STORE_LIST,
                null,
                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<StoreItem>>>() {
                }.getType(),
                requestTag,
                webApiListener,
                fragment
        );
    }

}