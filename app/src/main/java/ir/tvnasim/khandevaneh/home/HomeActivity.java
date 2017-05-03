package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.AuthHelper;
import ir.tvnasim.khandevaneh.account.login.LoginActivity;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.SharedPreferencesHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.SliderBanner;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.Token;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.UserInfo;
import ir.tvnasim.khandevaneh.view.bannerslider.BannerFragment;
import ir.tvnasim.khandevaneh.view.bannerslider.OnBannerClickedListener;

public class HomeActivity extends BaseActivity implements OnBannerClickedListener {

    private RecyclerView mMenuItemRecyclerView;
    private HomeMenuAdapter mHomeMenuAdapter;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, HomeActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        authenticate();

        findViews();
        initRecyclerView();
        setOnClickListeners();

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
            WebApiHelper.getUserInfo("requestTag_homeActivity_getUserInfo", new WebApiRequest.WebApiListener<UserInfo>() {
                @Override
                public void onResponse(UserInfo userInfo) {
                    User.getInstance().setFirstName(userInfo.getFirstName());
                    User.getInstance().setLastName(userInfo.getLastName());
                    User.getInstance().setAvatar(userInfo.getAvatar());
                    User.getInstance().setMelonScore(userInfo.getMelonScore());
                    User.getInstance().setExperienceScore(userInfo.getExperienceScore());
                }

                @Override
                public void onErrorResponse(String errorMessage) {

                }}, null).send();

        } else {
            String refreshToken = SharedPreferencesHelper.retrieveRefreshToken();
            if (refreshToken != null) {
                // We may could get a valid access token by refresh token
                WebApiHelper.authenticateWithRefreshToken(refreshToken, "requestTag_homeActivity_authWithRefreshToken", new WebApiRequest.WebApiListener<Token>() {
                    @Override
                    public void onResponse(Token token) {
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

    private void setOnClickListeners() {
//        mBannersSliderView.setOnBannerClickListener(this);
    }

    private void fetchBannersFromApi() {

        WebApiHelper.getSliderBanners("requestTag_homeActivity_getSliderBanners", new WebApiRequest.WebApiListener<ArrayList<SliderBanner>>() {
            @Override
            public void onResponse(ArrayList<SliderBanner> sliderBanners) {
                ArrayList<Bundle> bannersList = new ArrayList<>();
                for (SliderBanner sliderBanner : sliderBanners) {
                    Bundle banner = new Bundle();
                    banner.putString(BannerFragment.KEY_ARG_IMAGE_URL, sliderBanner.getImageUrl());
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
                    Toast.makeText(HomeActivity.this, bundle.getString(BannerFragment.KEY_ARG_IMAGE_URL), Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.start(HomeActivity.this);
                }
            }
        });
    }

}
