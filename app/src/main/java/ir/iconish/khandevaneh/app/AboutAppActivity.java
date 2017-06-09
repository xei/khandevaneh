package ir.iconish.khandevaneh.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.view.XeiTextView;


public class AboutAppActivity extends BaseActivity {

    private static final String TAG_REQUEST_GET_ABOUT_APP = "requestTag_getAboutApp";

    private XeiTextView mAboutAppTextView;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, AboutAppActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_ABOUT_APP);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        findViews();
        setTextViewAsScrollable();

        fetchData();
    }

    private void findViews() {
        mAboutAppTextView = (XeiTextView) findViewById(R.id.activityAboutApp_xeiTextView_aboutApp);
    }

    private void setTextViewAsScrollable() {
        mAboutAppTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void bindAboutAppData(String aboutAppStr) {
        mAboutAppTextView.setText("\n" + aboutAppStr.trim() + "\n");
    }

    private void fetchData() {
        WebApiHelper.getStartupConfig(TAG_REQUEST_GET_ABOUT_APP, new WebApiRequest.WebApiListener<StartupConfig>() {
            @Override
            public void onResponse(StartupConfig response, ScoresContainer scoresContainer) {
                if (response != null) {
                    bindAboutAppData(response.getAboutApp());
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getStartupConfig request (for getting aboutApp) failed: " + errorMessage);
            }
        }, null).send();
    }

}
