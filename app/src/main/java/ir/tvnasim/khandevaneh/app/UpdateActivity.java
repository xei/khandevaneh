package ir.tvnasim.khandevaneh.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.view.XeiButton;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_DEBUG = UpdateActivity.class.getSimpleName();

    private static final String KEY_EXTRA_URL_APK = "KEY_EXTRA_URL_APK";

    private XeiButton mDownloadButton;

    private String mApkUrl;

    public static void start(Context starter, String apkUrl) {
        Intent intent = new Intent(starter, UpdateActivity.class);
        intent.putExtra(KEY_EXTRA_URL_APK, apkUrl);
        starter.startActivity(intent);
    }

    private void getArguments() {
        mApkUrl = getIntent().getStringExtra(KEY_EXTRA_URL_APK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getArguments();

        findViews();

        setOnClickListeners();

    }

    private void findViews() {
        mDownloadButton = (XeiButton) findViewById(R.id.activityUpdate_xeiButton_download);
    }

    private void setOnClickListeners() {
        mDownloadButton.setOnClickListener(this);
    }

    private void download(String apkUrl) throws Exception{
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(apkUrl));
        startActivity(intent);
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.activityUpdate_xeiButton_download:
                try {
                    download(mApkUrl);
                } catch (Exception e) {
                    LogHelper.logError(TAG_DEBUG, "invalid APK URL: " + mApkUrl + '\n' + e.getMessage());
                }
                break;
        }
    }

}
