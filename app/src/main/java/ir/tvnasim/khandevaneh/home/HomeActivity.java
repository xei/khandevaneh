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
import ir.tvnasim.khandevaneh.account.LoginActivity;
import ir.tvnasim.khandevaneh.account.ProfileActivity;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.SharedPreferencesHelper;
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

        authenticate();

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
            //TODO: get UserInfo
            User.getInstance().setName("علیرضا");
            User.getInstance().setAvatar("http://img.bisms.ir/2015/06/rambod-javan-2.jpg");
            User.getInstance().setMelonScore(200);
            User.getInstance().setExperienceScore(300);
        } else {
            String refreshToken = SharedPreferencesHelper.retrieveRefreshToken();
            if (refreshToken != null) {
                // We may could get a valid access token by refresh token
                // TODO: call auth with refresh token and if ok do the following
                User.getInstance().setAccessToken("ACCESS-TOKEN");
                User.getInstance().setRefreshToken("REFRESH-TOKEN");
                SharedPreferencesHelper.storeAccessToken("ACCESS-TOKEN");
                SharedPreferencesHelper.storeRefreshToken("REFRESH-TOKEN");
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

        //TODO: fetch from API
        ArrayList<Bundle> list = new ArrayList<>();
        Bundle banner = new Bundle();
        banner.putString(BannerFragment.KEY_ARG_IMAGE_URL, "http://khndvanh.ir/wp-content/uploads/Untitled-3-5.jpg");
        for (int i = 0 ; i < 5 ; i++) {
            list.add(banner);
        }
        mHomeMenuAdapter.setSliderBanners(list);
        mHomeMenuAdapter.notifyDataSetChanged();
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
