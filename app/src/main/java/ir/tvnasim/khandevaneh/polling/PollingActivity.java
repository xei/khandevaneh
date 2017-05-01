package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;

public class PollingActivity extends BaseActivity {

    public static void start(Context starter) {
        Intent intent = new Intent(starter, PollingActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_polling;
    }

}
