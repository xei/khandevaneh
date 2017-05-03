package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.webapi.MockApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.view.XeiButton;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class PollingActivity extends BaseActivity {

    private static final String KEY_EXTRA_POLLING_ID = "KEY_EXTRA_POLLING_ID";

    private XeiTextView mTitleTextView;
    private XeiTextView mDescriptionTextView;
    private ListView mOptionsListView;
    private OptionsListAdapter mOptionsListAdapter;
    private XeiButton mPollButton;
    private XeiButton mShowStatisticsButton;

    private String mPollingId;
    private ArrayList<PollingOption> mOptions = new ArrayList<>();
    private OptionType mOptionsType = new OptionType();
    private ArrayList<String> mSelectedOptions = new ArrayList<>();

    public static void start(Context starter, String pollingId) {
        Intent intent = new Intent(starter, PollingActivity.class);
        intent.putExtra(KEY_EXTRA_POLLING_ID, pollingId);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        this.mPollingId = getIntent().getStringExtra(KEY_EXTRA_POLLING_ID);

        findViews();
        initOptionsListView();
        setOnClickListeners();

        fetchPollingFromApi();
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_polling;
    }

    private void findViews() {
        mTitleTextView = (XeiTextView) findViewById(R.id.activityPolling_xeiTextView_title);
        mDescriptionTextView = (XeiTextView) findViewById(R.id.activityPolling_xeiTextView_description);
        mOptionsListView = (ListView) findViewById(R.id.activityPolling_listView_options);
        mPollButton = (XeiButton) findViewById(R.id.activityPolling_xeiButton_poll);
        mShowStatisticsButton = (XeiButton) findViewById(R.id.activityPolling_xeiButton_showStatistics);
    }

    private void initOptionsListView() {
        mOptionsListAdapter = new OptionsListAdapter(mOptions, mOptionsType, mSelectedOptions);
        mOptionsListView.setAdapter(mOptionsListAdapter);
    }

    private void setOnClickListeners() {
        mPollButton.setOnClickListener(this);
        mShowStatisticsButton.setOnClickListener(this);
    }

    private void fetchPollingFromApi() {
        WebApiHelper.getPollingItem(mPollingId, "requestTag_pollingActivity_getPollingItem", new MockApiRequest.WebApiListener<PollingItem>() {
            @Override
            public void onResponse(PollingItem pollingItem) {
                mTitleTextView.setText(pollingItem.getTitle());
                mDescriptionTextView.setText(pollingItem.getDescription());
                mOptions.clear();
                mOptions.addAll(pollingItem.getOptions());
                mOptionsType.setType(pollingItem.getOptionType());

                //TODO: remove this
                PollingOption op = new PollingOption();
                op.setTitle("onvan");
                op.setId("0");
                op.setImageUrl("http://deeplearning.ir/wp-content/uploads/2016/03/deeplearning.ir_-768x360.jpg");

                PollingOption op1 = new PollingOption();
                op1.setTitle("onvan");
                op1.setId("1");
                op1.setImageUrl("http://deeplearning.ir/wp-content/uploads/2016/03/deeplearning.ir_-768x360.jpg");

                PollingOption op2 = new PollingOption();
                op2.setTitle("onvan");
                op2.setId("2");
                op2.setImageUrl("http://deeplearning.ir/wp-content/uploads/2016/03/deeplearning.ir_-768x360.jpg");

                PollingOption op3 = new PollingOption();
                op3.setTitle("onvan");
                op3.setId("3");
                op3.setImageUrl("http://deeplearning.ir/wp-content/uploads/2016/03/deeplearning.ir_-768x360.jpg");

                mOptions.clear();
                mOptions.add(op);
                mOptions.add(op1);
                mOptions.add(op2);
                mOptions.add(op3);

                mOptionsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityPolling_xeiButton_poll:
                // TODO: submit poll
                finish();
                break;

            case R.id.activityPolling_xeiButton_showStatistics:
                //TODO: open statistics activity
                break;
        }
    }
}
