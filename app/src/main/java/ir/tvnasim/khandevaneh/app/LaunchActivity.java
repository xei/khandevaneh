package ir.tvnasim.khandevaneh.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.profile.ProfileActivity;
import ir.tvnasim.khandevaneh.exception.InValidDestinationException;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.VolleyHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.home.HomeActivity;
import ir.tvnasim.khandevaneh.leaderboard.LeaderBoardActivity;
import ir.tvnasim.khandevaneh.livelike.LiveLikeActivity;
import ir.tvnasim.khandevaneh.polling.PollingActivity;
import ir.tvnasim.khandevaneh.polling.PollingListActivity;
import ir.tvnasim.khandevaneh.polling.PollingStatisticsActivity;
import ir.tvnasim.khandevaneh.store.StoreActivity;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;

public class LaunchActivity extends AppCompatActivity {

    protected static final String TAG_DEBUG = LaunchActivity.class.getSimpleName();

    public static final String KEY_DESTINATION = "KEY_DESTINATION";
    public static final String KEY_PARAM = "KEY_PARAM";

    protected static final String TAG_REQUEST_GET_STARTUP_CONFIG = "requestTag_launchActivity_getStartupConfig";

    public static final String DESTINATION_NO_NETWORK = "NoNetworkActivity";
    public static final String DESTINATION_COMPETITION_LIST = "2";
    public static final String DESTINATION_COMPETITION = "3";
    public static final String DESTINATION_COMPETITION_STATISTICS = "4";
    public static final String DESTINATION_LIVE_LIKE = "1";
    public static final String DESTINATION_POLLING_LIST = "5";
    public static final String DESTINATION_POLLING = "6";
    public static final String DESTINATION_POLLING_STATISTICS = "7";
    public static final String DESTINATION_STORE = "9";
    public static final String DESTINATION_LEADER_BOARD = "";
    public static final String DESTINATION_CAMPAIGN = "";
    public static final String DESTINATION_ARCHIVE = "";
    public static final String DESTINATION_AWARDS = "";
    public static final String DESTINATION_PROFILE = "8";
    public static final String DESTINATION_BROWSER = "Browser";

    public static void start(Context starter) {
        Intent intent = new Intent(starter, LaunchActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        handleFcmIntent();

        if (HelperFunctions.isNetworkConnected()) {
            WebApiHelper.getStartupConfig(TAG_REQUEST_GET_STARTUP_CONFIG, new WebApiRequest.WebApiListener<StartupConfig>() {
                @Override
                public void onResponse(StartupConfig startupConfig, ScoresContainer scoresContainer) {
                    openAppropriateActivity(startupConfig);
                }

                @Override
                public void onErrorResponse(String errorMessage) {
                    Log.e(TAG_DEBUG, "getStartupConfig request failed!");
                }
            }, null).send();
        } else {
            NoNetworkActivity.start(this, LaunchActivity.class.getSimpleName());
            finish();
        }

    }

    protected void handleFcmIntent() {

        String destination = getIntent().getStringExtra(KEY_DESTINATION);
        String param = getIntent().getStringExtra(KEY_PARAM);
        try {
            goTo(this, destination, param);
        } catch (InValidDestinationException ivde) {
            LogHelper.logError(TAG_DEBUG, ivde.getMessage());
        }
    }

    private void openAppropriateActivity(StartupConfig startupConfig) {
        if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_VALID) {
            HomeActivity.start(this);
            finish();
        } else if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_OLD) {
            new KhandevanehDialog(this, "نسخه جدیدمون رو بگیر بابا کلی چیز باحال توشه...", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.start(LaunchActivity.this);
                    finish();
                }
            }).show();
        } else if (startupConfig.getVersionState() == StartupConfig.VERSION_STATE_DEPRECATED) {
            UpdateActivity.start(this, startupConfig.getLatestApk());
            finish();
        } else {
            LogHelper.logError(TAG_DEBUG, "invalid version state!");
            finish();
        }
    }

    public static void goTo(Context starterContext, String destination, String param) throws InValidDestinationException {
        if (destination != null) {
            switch (destination) {
                case DESTINATION_NO_NETWORK:
                    NoNetworkActivity.start(starterContext, starterContext.getClass().getSimpleName());
                    break;
                case DESTINATION_COMPETITION_LIST:
                    PollingListActivity.start(starterContext, PollingListActivity.TYPE_COMPETITION);
                    break;

                case DESTINATION_COMPETITION:
                    PollingActivity.start(starterContext, PollingListActivity.TYPE_COMPETITION, param);
                    break;

                case DESTINATION_COMPETITION_STATISTICS:
                    PollingStatisticsActivity.start(starterContext, param, ""); // TITLE?
                    break;

                case DESTINATION_LIVE_LIKE:
                    LiveLikeActivity.start(starterContext);
                    break;

                case DESTINATION_POLLING_LIST:
                    PollingListActivity.start(starterContext, PollingListActivity.TYPE_POLLING);
                    break;

                case DESTINATION_POLLING:
                    PollingActivity.start(starterContext, PollingListActivity.TYPE_POLLING, param);
                    break;

                case DESTINATION_POLLING_STATISTICS:
                    PollingStatisticsActivity.start(starterContext, param, ""); // TITLE?
                    break;

                case DESTINATION_STORE:
                    StoreActivity.start(starterContext);
                    break;

                case DESTINATION_LEADER_BOARD:
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

                case DESTINATION_PROFILE:
                    ProfileActivity.start(starterContext);
                    break;

                case DESTINATION_BROWSER:
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(param));
                        starterContext.startActivity(intent);
                    } catch (Exception e) {
                        LogHelper.logError(TAG_DEBUG, e.getMessage());
                    }
                    break;

                default:
                    throw new InValidDestinationException(destination, param);
            }
        } else {
            throw new InValidDestinationException(destination, param);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyHelper.cancelPendingRequests(TAG_REQUEST_GET_STARTUP_CONFIG);
    }

}
