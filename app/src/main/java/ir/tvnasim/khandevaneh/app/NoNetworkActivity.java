package ir.tvnasim.khandevaneh.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class NoNetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_EXTRA_STARTER_NAME = "KEY_EXTRA_STARTER_NAME";

    private XeiTextView mRetryBtnTextView;
    private XeiTextView mCheckSettingsBtnTextView;

    private String mStarterName;

    public static void start(Context starter, String starterName) {
        Intent intent = new Intent(starter, NoNetworkActivity.class);
        intent.putExtra(KEY_EXTRA_STARTER_NAME, starterName);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);

        mStarterName = getIntent().getStringExtra(KEY_EXTRA_STARTER_NAME);

        findViews();
        setOnClickListeners();
    }

    private void findViews() {
        mRetryBtnTextView = (XeiTextView) findViewById(R.id.activityNoNetwork_xeiTextView_retry);
        mCheckSettingsBtnTextView = (XeiTextView) findViewById(R.id.activityNoNetwork_xeiTextView_checkSettings);
    }

    private void setOnClickListeners() {
        mRetryBtnTextView.setOnClickListener(this);
        mCheckSettingsBtnTextView.setOnClickListener(this);
    }

    private void retry() {
        if (HelperFunctions.isNetworkConnected()) {
            if (mStarterName.equals(LaunchActivity.class.getSimpleName())) {
                LaunchActivity.start(this);
            }
            finish();
        }
    }

    private void openNetworkSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    private void openAndroidLauncherHomeScreen() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (HelperFunctions.isNetworkConnected()) {
            if (mStarterName.equals(LaunchActivity.class.getSimpleName())) {
                LaunchActivity.start(this);
            } else {
                finish();
            }
        } else {
            openAndroidLauncherHomeScreen();
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.activityNoNetwork_xeiTextView_retry:
                retry();
                break;

            case R.id.activityNoNetwork_xeiTextView_checkSettings:
                openNetworkSettings();
                break;
        }
    }

}
