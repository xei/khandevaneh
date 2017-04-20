package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.AuthHelper;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.SharedPreferencesHelper;

public class HomeActivity extends BaseActivity {

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

        autoLogin();

        findViews();
        initRecyclerView();
        setOnClickListeners();

    }

    private void autoLogin() {
        String accessToken = SharedPreferencesHelper.retrieveAccessToken();
        if (accessToken != null && AuthHelper.isTokenValid(accessToken)) {
            User.getInstance().setAccessToken(accessToken);
            //TODO: get UserInfo
            User.getInstance().setName("علیرضا");
            User.getInstance().setAvatar("http://img.bisms.ir//2015/06/rambod-javan-2.jpg");
            User.getInstance().setMelonScore(200);
            User.getInstance().setExperienceScore(300);
        } else {
            String refreshToken = SharedPreferencesHelper.retrieveRefreshToken();
            if (refreshToken != null) {
                // We may could get a valid access token
                // TODO: call auth with refresh token and if ok do the following
                User.getInstance().setAccessToken("ACCESS-TOKEN");
                User.getInstance().setRefreshToken("REFRESH-TOKEN");
                SharedPreferencesHelper.storeAccessToken("ACCESS-TOKEN");
                SharedPreferencesHelper.storeRefreshToken("REFRESH-TOKEN");
            }
        }
    }

    private void findViews() {
        mMenuItemRecyclerView = (RecyclerView) findViewById(R.id.activityHome_recyclerView_menuItems);
    }

    private void initRecyclerView() {
        mMenuItemRecyclerView.setHasFixedSize(true);
        mMenuItemRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mHomeMenuAdapter = new HomeMenuAdapter();
        mMenuItemRecyclerView.setAdapter(mHomeMenuAdapter);
    }

    private void setOnClickListeners() {

    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

    }
}
