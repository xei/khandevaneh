package ir.tvnasim.khandevaneh.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    public static void start(Context starter) {
        Intent intent = new Intent(starter, LoginActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
