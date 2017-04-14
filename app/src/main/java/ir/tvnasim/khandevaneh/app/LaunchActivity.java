package ir.tvnasim.khandevaneh.app;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ir.tvnasim.khandevaneh.home.HomeActivity;
import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.util.LogHelper;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //TODO: getStartupConfig (APP_ID:APP_SECRET)
        // getVersionCode
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartupConfig startupConfig = new StartupConfig();
                startupConfig.setVersionState(1);
                openAppropriateActivity(startupConfig);
            }
        }, 2000);



    }

    private void openAppropriateActivity(StartupConfig startupConfig) {
        if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_NEED_UPDATE) {
            UpdateActivity.start(this, startupConfig.getLatestApk());
        } else if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_VALID_VERSION) {
            HomeActivity.start(this);
        } else {
            LogHelper.logError(TAG_DEBUG, "invalid version state!");
        }
        finish();
    }

}
