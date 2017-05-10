package ir.tvnasim.khandevaneh.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.helper.imageloading.ImageLoader;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;

public class LeaderBoardActivity extends BaseActivity {

    private TextView mUserNameTextView;
    private SimpleDraweeView mUserAvatarSimpleDraweeView;
    private TextView mUserExperienceTextView;
    private RecyclerView mLeaderBoardRecyclerView;
    private LeaderBoardAdapter mLeaderBoardAdapter;

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
        mUserNameTextView = (TextView) findViewById(R.id.layoutLeaderboardUser_textView_userName);
        mUserAvatarSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.layoutLeaderboardUser_simpleDraweeView_userAvatar);
        mUserExperienceTextView = (TextView) findViewById(R.id.activityLeaderboard_textView_userExperience);
        mLeaderBoardRecyclerView = (RecyclerView) findViewById(R.id.activityLeaderboard_recyclerView_leaders);
    }

    private void initRecyclerView() {
        mLeaderBoardRecyclerView.setHasFixedSize(true);
        mLeaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeaderBoardAdapter = new LeaderBoardAdapter(mLeaders);
        mLeaderBoardRecyclerView.setAdapter(mLeaderBoardAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        renderUserData();
        fetchDataFromApi();
    }

    private void renderUserData() {
        mUserNameTextView.setText(User.getInstance().getFirstName() + ' ' + User.getInstance().getLastName());
        FrescoHelper.setImageUrl(mUserAvatarSimpleDraweeView, User.getInstance().getAvatar());
        mUserExperienceTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(User.getInstance().getExperienceScore())));
    }

    private void fetchDataFromApi() {

        WebApiHelper.getLeaderBoard("requestTag_leaderBoardActivity_getLeaderBoard", new WebApiRequest.WebApiListener<LeaderContainerModel>() {
            @Override
            public void onResponse(LeaderContainerModel leaders) {
                bindData(leaders.getLeaders());
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();

    }

    private void bindData(ArrayList<LeaderViewModel> leaders) {
        mLeaders.clear();
        mLeaders.addAll(leaders);
        mLeaderBoardAdapter.notifyDataSetChanged();
    }
}
