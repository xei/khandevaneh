package ir.iconish.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.account.AuthHelper;
import ir.iconish.khandevaneh.account.Token;
import ir.iconish.khandevaneh.account.User;
import ir.iconish.khandevaneh.account.UserInfo;
import ir.iconish.khandevaneh.account.login.LoginActivity;
import ir.iconish.khandevaneh.app.Banner;
import ir.iconish.khandevaneh.app.BaseActivity;
import ir.iconish.khandevaneh.app.LaunchActivity;
import ir.iconish.khandevaneh.app.ScoresContainer;
import ir.iconish.khandevaneh.exception.InValidDestinationException;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.SharedPreferencesHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.view.bannerslider.BannerFragment;
import ir.iconish.khandevaneh.view.bannerslider.OnBannerClickedListener;

public class HomeActivity extends BaseActivity implements OnBannerClickedListener {

    private static final String TAG_REQUEST_GET_USER_INFO = "requestTag_homeActivity_getUserInfo";
    private static final String TAG_REQUEST_AUTH_WITH_REFRESH_TOKEN = "requestTag_homeActivity_authWithRefreshToken";
    private static final String TAG_REQUEST_GET_SLIDER_BANNERS = "requestTag_homeActivity_getSliderBanners";
    private RecyclerView mMenuItemRecyclerView;
    private HomeMenuAdapter mHomeMenuAdapter;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, HomeActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_USER_INFO);
        tags.add(TAG_REQUEST_AUTH_WITH_REFRESH_TOKEN);
        tags.add(TAG_REQUEST_GET_SLIDER_BANNERS);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        authenticate();

        findViews();
        initRecyclerView();

        fetchBannersFromApi();
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_home;
    }

    // TODO: think about move this to launch activity
    private void authenticate() {
        String accessToken = SharedPreferencesHelper.retrieveAccessToken();
        if (accessToken != null && AuthHelper.isTokenValid(accessToken)) {
            // There exist an access token
            User.getInstance().setAccessToken(accessToken);
            WebApiHelper.getUserInfo(TAG_REQUEST_GET_USER_INFO, new WebApiRequest.WebApiListener<UserInfo>() {
                @Override
                public void onResponse(UserInfo userInfo, ScoresContainer scoresContainer) {
                    User.getInstance().setUserInfo(userInfo);
                    showScores();
                }

                @Override
                public void onErrorResponse(String errorMessage) {
                    LogHelper.logError(TAG_DEBUG, "getUserInfo request failed!");
                }
            }, null).send();

        } else {
            String refreshToken = SharedPreferencesHelper.retrieveRefreshToken();
            if (refreshToken != null) {
                // We may could get a valid access token by refresh token
                WebApiHelper.authenticateWithRefreshToken(refreshToken,TAG_REQUEST_AUTH_WITH_REFRESH_TOKEN , new WebApiRequest.WebApiListener<Token>() {
                    @Override
                    public void onResponse(Token token, ScoresContainer scoresContainer) {
                        User.getInstance().setAccessToken(token.getAcessToken());
                        User.getInstance().setRefreshToken(token.getRefreshToken());
                        SharedPreferencesHelper.storeAccessToken(token.getAcessToken());
                        SharedPreferencesHelper.storeRefreshToken(token.getRefreshToken());
                    }

                    @Override
                    public void onErrorResponse(String errorMessage) {

                    }
                }, null).send();

            } else {
                // Need to authenticate with phone number.
                //TODO: if move this method to LauncherActivity then must handle this else and open login screen
            }
        }
    }

    private void findViews() {
        mMenuItemRecyclerView = (RecyclerView) findViewById(R.id.activityHome_recyclerView_menuItems);
    }

    private void initRecyclerView() {
        mMenuItemRecyclerView.setHasFixedSize(true);
        mHomeMenuAdapter = new HomeMenuAdapter();
        mMenuItemRecyclerView.setLayoutManager(new HomeMenuLayoutManager(this, mHomeMenuAdapter));
        mMenuItemRecyclerView.setAdapter(mHomeMenuAdapter);
    }

    private void fetchBannersFromApi() {

        WebApiHelper.getSliderBanners(TAG_REQUEST_GET_SLIDER_BANNERS, new WebApiRequest.WebApiListener<ArrayList<Banner>>() {
            @Override
            public void onResponse(ArrayList<Banner> sliderBanners, ScoresContainer scoresContainer) {
                ArrayList<Bundle> bannersList = new ArrayList<>();
                for (Banner sliderBanner : sliderBanners) {
                    Bundle banner = new Bundle();
                    banner.putString(BannerFragment.KEY_ARG_IMAGE_URL, sliderBanner.getImageUrl());
                    banner.putString(BannerFragment.KEY_ARG_DESTINATION, sliderBanner.getDestination());
                    banner.putString(BannerFragment.KEY_ARG_DESTINATION_PARAM, sliderBanner.getDestinationParam());
                    bannersList.add(banner);
                }
                mHomeMenuAdapter.setSliderBanners(bannersList);
                mHomeMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();

    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);
    }

    @Override
    public void onBannerClick(final Bundle bundle) {
        User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
            @Override
            public void isLoggedIn(boolean isLoggedIn) {
                if (isLoggedIn) {
                    String destination = bundle.getString(BannerFragment.KEY_ARG_DESTINATION);
                    String destinationParam = bundle.getString(BannerFragment.KEY_ARG_DESTINATION_PARAM);
                    try {
                        LaunchActivity.goTo(HomeActivity.this, destination, destinationParam);
                    } catch (InValidDestinationException ide) {
                        LogHelper.logError(TAG_DEBUG, ide.getMessage());
                    }
                } else {
                    LoginActivity.start(HomeActivity.this);
                }
            }
        });
    }

}
