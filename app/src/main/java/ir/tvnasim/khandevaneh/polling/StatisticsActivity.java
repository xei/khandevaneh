package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;

public class StatisticsActivity extends AppCompatActivity {

    private static final String KEY_EXTRA_POLLING_ID = "KEY_EXTRA_POLLING_ID";

    private String mPollingId;

    public static void start(Context starter, String pollingId) {
        Intent intent = new Intent(starter, StatisticsActivity.class);
        intent.putExtra(KEY_EXTRA_POLLING_ID, pollingId);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        this.mPollingId = getIntent().getStringExtra(KEY_EXTRA_POLLING_ID);

    }
}
