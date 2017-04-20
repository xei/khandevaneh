package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.livelike.LiveLikeActivity;

public class ArchiveActivity extends BaseActivity {

    public static void start(Context starter) {
        Intent intent = new Intent(starter, ArchiveActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
    }
}
