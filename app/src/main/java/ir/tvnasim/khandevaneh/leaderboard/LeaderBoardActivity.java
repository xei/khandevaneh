package ir.tvnasim.khandevaneh.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;

public class LeaderBoardActivity extends BaseActivity {

    private TextView mUserNameTextView;
    private ImageView mUserAvatarImageView;
    private TextView mUserExperienceTextView;
    private RecyclerView mLeaderBoardRecyclerView;

    private ArrayList<LeaderViewModel> mLeaders = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, LeaderBoardActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        findViews();
        initRecyclerView();
    }

    private void findViews() {
        mUserNameTextView = (TextView) findViewById(R.id.activityLeaderboard_textView_userName);
        mUserAvatarImageView = (ImageView) findViewById(R.id.activityLeaderboard_imageView_userAvatar);
        mUserExperienceTextView = (TextView) findViewById(R.id.activityLeaderboard_textView_userExperience);
        mLeaderBoardRecyclerView = (RecyclerView) findViewById(R.id.activityLeaderboard_recyclerView_leaders);
    }

    private void initRecyclerView() {
        mLeaderBoardRecyclerView.setHasFixedSize(true);
        mLeaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeaderBoardRecyclerView.setAdapter(new LeaderBoardAdapter(mLeaders));
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
        mUserAvatarImageView.setImageResource(R.drawable.background_splash);
        mUserExperienceTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(User.getInstance().getExperienceScore())));
    }

    private void fetchDataFromApi() {
        //TODO: API call
        ArrayList<LeaderViewModel> leaders = new ArrayList<>();
        LeaderViewModel leaderViewModel = new LeaderViewModel();
        leaderViewModel.setName("حمیدرضا");
        leaderViewModel.setExperience("200");
        LeaderViewModel leaderViewModel2 = new LeaderViewModel();
        leaderViewModel2.setName("علیرضا");
        leaderViewModel2.setExperience("300");
        for (int i = 0 ; i < 50 ; i++) {
            leaders.add(leaderViewModel);
            leaders.add(leaderViewModel2);
        }
        bindData(leaders);

    }

    private void bindData(ArrayList<LeaderViewModel> leaders) {
        mLeaders.clear();
        mLeaders.addAll(leaders);
    }
}
