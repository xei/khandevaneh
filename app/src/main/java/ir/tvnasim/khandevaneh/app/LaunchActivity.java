package ir.tvnasim.khandevaneh.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.StartupConfig;
import ir.tvnasim.khandevaneh.home.HomeActivity;

public class LaunchActivity extends AppCompatActivity {

    protected static final String TAG_DEBUG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //TODO: getStartupConfig (APP_ID:APP_SECRET)
        WebApiHelper.getStartupConfig("TAG_REQUEST_GET_STARTUP_CONFIG", new WebApiRequest.WebApiListener<StartupConfig>() {
            @Override
            public void onResponse(StartupConfig startupConfig) {
                openAppropriateActivity(startupConfig);
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();

    }

    private void openAppropriateActivity(StartupConfig startupConfig) {
        if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_VALID) {
            HomeActivity.start(this);
        } else if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_OLD) {
            Toast.makeText(this, "نسخه جدیدی از اپلیکیشن منتشر شده. لطفا آپدیت کنید!", Toast.LENGTH_SHORT).show();
        } else if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_DEPRECATED) {
            UpdateActivity.start(this, startupConfig.getLatestApk());
        } else {
            LogHelper.logError(TAG_DEBUG, "invalid version state!");
        }
        finish();
    }

}
