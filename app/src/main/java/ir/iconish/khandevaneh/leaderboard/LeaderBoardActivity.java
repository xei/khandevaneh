package ir.iconish.khandevaneh.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.app.BaseActivity;
import ir.iconish.khandevaneh.app.ScoresContainer;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;

public class LeaderBoardActivity extends BaseActivity {

    private static final String TAG_REQUEST_GET_LEADER_BOARD = "requestTag_leaderBoardActivity_getLeaderBoard";

    private RecyclerView mLeaderBoardRecyclerView;
    private LeaderBoardAdapter mLeaderBoardAdapter;

    private ArrayList<LeaderViewModel> mLeaders = new ArrayList<>();


    public static void start(Context starter) {
        Intent intent = new Intent(starter, LeaderBoardActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_LEADER_BOARD);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        findViews();
        initRecyclerView();
    }

    private void findViews() {
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

        fetchDataFromApi();
    }

    private void bindData(ArrayList<LeaderViewModel> leaders, int userRank) {
        mLeaders.clear();
        mLeaders.addAll(leaders);
        mLeaderBoardAdapter.setUserRank(userRank);
        mLeaderBoardAdapter.notifyDataSetChanged();
    }

    private void fetchDataFromApi() {

        WebApiHelper.getLeaderBoard(TAG_REQUEST_GET_LEADER_BOARD, new WebApiRequest.WebApiListener<LeaderContainerModel>() {
            @Override
            public void onResponse(LeaderContainerModel leaders, ScoresContainer scoresContainer) {
                bindData(leaders.getLeaders(), leaders.getUserRank());
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getLeaderBoard request failed: " + errorMessage);
            }
        }, null).send();

    }

}