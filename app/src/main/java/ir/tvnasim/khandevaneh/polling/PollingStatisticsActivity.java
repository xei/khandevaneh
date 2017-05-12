package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class PollingStatisticsActivity extends BaseActivity {

    private static final String KEY_EXTRA_POLLING_ID = "KEY_EXTRA_POLLING_ID";
    private static final String KEY_EXTRA_POLLING_TITLE = "KEY_EXTRA_POLLING_TITLE";
    private static final String TAG_REQUEST_GET_POLLING_STATISTICS = "requestTag_pollingStatisticsActivity_getPollingStatistics";

    private XeiTextView mTitleTextView;
    private XeiTextView mDescriptionTextView;
    private ListView mStatisticsListView;
    private PollingStatisticsListAdapter mStatisticsListAdapter;

    private String mPollingId;
    private String mPollingTitle;
    private ArrayList<PollingStatisticsItem> mStatistics = new ArrayList<>();

    public static void start(Context starter, String pollingId, String pollingTitle) {
        Intent intent = new Intent(starter, PollingStatisticsActivity.class);
        intent.putExtra(KEY_EXTRA_POLLING_ID, pollingId);
        intent.putExtra(KEY_EXTRA_POLLING_TITLE, pollingTitle);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_POLLING_STATISTICS);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_statistics);

        getArguments();

        findViews();
        renderTitleView();
        initOptionsListView();

        fetchStatisticsFromApi();
    }

    private void getArguments() {
        this.mPollingId = getIntent().getStringExtra(KEY_EXTRA_POLLING_ID);
        this.mPollingTitle = getIntent().getStringExtra(KEY_EXTRA_POLLING_TITLE);
    }

    private void findViews() {
        mTitleTextView = (XeiTextView) findViewById(R.id.activityPollingStatistics_xeiTextView_title);
        mDescriptionTextView = (XeiTextView) findViewById(R.id.activityPollingStatistics_xeiTextView_description);
        mStatisticsListView = (ListView) findViewById(R.id.activityPollingStatistics_listView_statistics);
    }

    private void renderTitleView() {
        mTitleTextView.setText(mPollingTitle);
    }

    private void initOptionsListView() {
        mStatisticsListAdapter = new PollingStatisticsListAdapter(mStatistics);
        mStatisticsListView.setAdapter(mStatisticsListAdapter);
    }

    private void fetchStatisticsFromApi() {

        WebApiHelper.getPollingStatistics(mPollingId, TAG_REQUEST_GET_POLLING_STATISTICS, new WebApiRequest.WebApiListener<ArrayList<PollingStatisticsItem>>() {
            @Override
            public void onResponse(ArrayList<PollingStatisticsItem> pollingStatisticsItems, ScoresContainer scoresContainer) {
                mStatistics.clear();
                mStatistics.addAll(pollingStatisticsItems);
                mStatisticsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getPollingStatistics request failed:" + errorMessage);
            }
        }, null).send();

    }
}
