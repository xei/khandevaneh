package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.tvnasim.khandevaneh.R;

public class ArchiveActivity extends AppCompatActivity {

    public static void start(Context starter, String archiveId) {
        Intent intent = new Intent(starter, ArchiveActivity.class);
        intent.putExtra("", archiveId);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
    }
}
