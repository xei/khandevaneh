package ir.tvnasim.khandevaneh.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ir.tvnasim.khandevaneh.R;

public class UpdateActivity extends AppCompatActivity {

    private static final String KEY_EXTRA_URL_APK = "URL_APK";

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

        ((TextView)findViewById(R.id.activityUpdate_textView_url)).setText(mApkUrl);
    }

}
