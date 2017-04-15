package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.AuthHelper;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.persistance.SharedPreferencesHelper;

public class HomeActivity extends BaseActivity {

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
        setOnClickListeners();

    }

    private void autoLogin() {
        String accessToken = SharedPreferencesHelper.retrieveAccessToken();
        if (accessToken != null && AuthHelper.isTokenValid(accessToken)) {
            User.getInstance().setAccessToken(accessToken);
            //TODO: get UserInfo
            User.getInstance().setName("علیرضا");
            User.getInstance().setAvatar("");
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

    }

    private void setOnClickListeners() {

    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

    }
}
