package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;

public class ArchiveListActivity extends BaseActivity {

    public static void start(Context starter) {
        Intent intent = new Intent(starter, ArchiveCategoriesActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_list);
    }
}
