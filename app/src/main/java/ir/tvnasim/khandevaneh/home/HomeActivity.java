package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.AuthHelper;
import ir.tvnasim.khandevaneh.account.ProfileActivity;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.persistance.SharedPreferencesHelper;
import ir.tvnasim.khandevaneh.util.LogHelper;

public class HomeActivity extends BaseActivity {

//    private ImageButton mProfileImageButton;

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
            //TODO: get Scores
            User.getInstance().setMelonScore(120);
            User.getInstance().setExperienceScore(350);
        } else {
            String refreshToken = SharedPreferencesHelper.retrieveRefreshToken();
            if (refreshToken != null) {
                // We may could get a valid access token
                // TODO: call auth with refresh token
                SharedPreferencesHelper.storeAccessToken("");
                SharedPreferencesHelper.storeRefreshToken("");
                User.getInstance().setAccessToken("");
                User.getInstance().setRefreshToken("");
            }
        }
    }

    private void findViews() {
//        mProfileImageButton = (ImageButton) findViewById(R.id.activityHome_imageButton_profile);
    }

    private void setOnClickListeners() {
//        mProfileImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityBase_imageButton_profile:
                ProfileActivity.start(this);
                break;
        }
    }
}
