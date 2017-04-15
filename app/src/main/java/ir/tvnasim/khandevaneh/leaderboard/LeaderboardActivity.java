package ir.tvnasim.khandevaneh.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.home.HomeActivity;

public class LeaderboardActivity extends BaseActivity {

    private TextView mUserNameTextView;
    private ImageView mUserAvatarImageView;
    private TextView mUserExperienceTextView;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, LeaderboardActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        findViews();
    }

    private void findViews() {
        mUserNameTextView = (TextView) findViewById(R.id.activityLeaderboard_textView_userName);
        mUserAvatarImageView = (ImageView) findViewById(R.id.ativityLeaderboard_imageView_userAvatar);
        mUserExperienceTextView = (TextView) findViewById(R.id.activityLeaderboard_textView_userExperience);
    }

    @Override
    protected void onResume() {
        super.onResume();

        renderUserData();
        fetchDataFromApi();
    }

    private void renderUserData() {
        mUserNameTextView.setText(User.getInstance().getName());
        //TODO: set Image url to fresco simple drawee view
        mUserExperienceTextView.setText(String.valueOf(User.getInstance().getExperienceScore()));
    }

    private void fetchDataFromApi() {
        //TODO: API call

    }

    private void bindData(ArrayList<LeaderViewModel> leaders) {
        // TODO: fill the leader board with a recyclerview
    }
}
