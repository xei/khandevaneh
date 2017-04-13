package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.AuthHelper;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.persistance.SharedPreferencesHelper;

public class HomeActivity extends AppCompatActivity {

    public static void start(Context starter) {
        Intent intent = new Intent();
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoLogin();

    }

    private void autoLogin() {
        String accessToken = SharedPreferencesHelper.retrieveAccessToken();
        if (accessToken != null && AuthHelper.isTokenValid(accessToken)) {
            User.getInstance().setAccessToken(accessToken);
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

}
