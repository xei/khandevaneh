package ir.iconish.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.HelperFunctions;

public class FullScreenVideoActivity extends AppCompatActivity {

    public static void start(Context starter, String videoUrl, int position) {
        Intent intent = new Intent(starter, FullScreenVideoActivity.class);
        intent.putExtra("url", videoUrl);
        intent.putExtra("pos", position);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

        String videoUrl = getIntent().getStringExtra("url");
        int pos = getIntent().getIntExtra("pos", 0);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.activityFullScreenVideo_progressBar_loading);

        final VideoView videoView = (VideoView) findViewById(R.id.activityFullScreenVideo_videoView_videoView);
        videoView.setVideoPath(videoUrl);
        videoView.seekTo(pos);
        videoView.setKeepScreenOn(true);

        final MediaController mediaController = new MediaController(this) {
            @Override
            public void show(int timeout) {
                super.show(0);
            }
        };
        mediaController.getLayoutParams().height = HelperFunctions.dpToPx(this, 80);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                progressBar.setVisibility(View.GONE);

                mediaController.setAnchorView(videoView);
                mediaController.setMediaPlayer(videoView);
                mediaController.show(0);
                mediaController.requestFocus();

//                maximizeVideoView(archiveContextVideoView);
            }
        });
    }
}
