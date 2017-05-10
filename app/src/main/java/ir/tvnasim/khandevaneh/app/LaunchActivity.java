package ir.tvnasim.khandevaneh.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.profile.ProfileActivity;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.Banner;
import ir.tvnasim.khandevaneh.helper.webapi.model.app.StartupConfig;
import ir.tvnasim.khandevaneh.home.HomeActivity;
import ir.tvnasim.khandevaneh.leaderboard.LeaderBoardActivity;
import ir.tvnasim.khandevaneh.livelike.LiveLikeActivity;
import ir.tvnasim.khandevaneh.polling.PollingActivity;
import ir.tvnasim.khandevaneh.polling.PollingListActivity;
import ir.tvnasim.khandevaneh.store.StoreActivity;

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

    public static void goTo(Context starterContext, String destination, String param) {
        switch (destination) {
            case Banner.DESTINATION_COMPETITION_LIST:
                PollingListActivity.start(starterContext, PollingListActivity.TYPE_COMPETITION);
                break;

            case Banner.DESTINATION_COMPETITION:
                PollingActivity.start(starterContext, param);
                break;

            case Banner.DESTINATION_COMPETITION_STATISTICS:
                //TODO: invoke statistics screen
                //TODO: id and param
                break;

            case Banner.DESTINATION_LIVE_LIKE:
                LiveLikeActivity.start(starterContext);
                break;

            case Banner.DESTINATION_POLLING_LIST:
                PollingListActivity.start(starterContext, PollingListActivity.TYPE_POLLING);
                break;

            case Banner.DESTINATION_POLLING:
                PollingActivity.start(starterContext, param);
                break;

            case Banner.DESTINATION_POLLING_STATISTICS:
                //TODO: invoke statistics screen
                //TODO: id and param
                break;

            case Banner.DESTINATION_STORE:
                StoreActivity.start(starterContext);
                break;

            case Banner.DESTINATION_LEADER_BOARD:
                LeaderBoardActivity.start(starterContext);
                break;
//
//            case Banner.DESTINATION_CAMPAIGN:
//                break;
//
//            case Banner.DESTINATION_ARCHIVE:
//                break;
//
//            case Banner.DESTINATION_AWARDS:
//                break;

            case Banner.DESTINATION_PROFILE:
                ProfileActivity.start(starterContext);
                break;

            default:
                //

        }
    }

}
