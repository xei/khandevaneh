package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.XeiButton;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class PollingActivity extends BaseActivity {

    private static final String KEY_EXTRA_POLLING_ID = "KEY_EXTRA_POLLING_ID";
    private static final String TAG_REQUEST_GET_POLLING_ITEM = "requestTag_pollingActivity_getPollingItem";
    private static final String TAG_REQUEST_POLL = "requestTag_pollingActivity_poll";

    private XeiTextView mTitleTextView;
    private XeiTextView mDescriptionTextView;
    private ListView mOptionsListView;
    private OptionsListAdapter mOptionsListAdapter;
    private RelativeLayout mFooterRelativeLayout;
    private XeiButton mPollButton;
    private XeiButton mShowStatisticsButton;

    private String mPollingId;
    private int mPolledBefore = PollingItem.POLLED_BEFORE_NOT_SET;
    private ArrayList<PollingOption> mOptions = new ArrayList<>();
    private OptionType mOptionsType = new OptionType();
    private ArrayList<String> mSelectedOptions = new ArrayList<>();

    public static void start(Context starter, String pollingId) {
        Intent intent = new Intent(starter, PollingActivity.class);
        intent.putExtra(KEY_EXTRA_POLLING_ID, pollingId);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_POLLING_ITEM);
        tags.add(TAG_REQUEST_POLL);
        return tags;
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

    private void findViews() {
        mTitleTextView = (XeiTextView) findViewById(R.id.activityPolling_xeiTextView_title);
        mDescriptionTextView = (XeiTextView) findViewById(R.id.activityPolling_xeiTextView_description);
        mOptionsListView = (ListView) findViewById(R.id.activityPolling_listView_options);
        mFooterRelativeLayout = (RelativeLayout) findViewById(R.id.activityPolling_relativeLayout_footer);
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
        WebApiHelper.getPollingItem(mPollingId, TAG_REQUEST_GET_POLLING_ITEM, new WebApiRequest.WebApiListener<PollingItem>() {
            @Override
            public void onResponse(PollingItem pollingItem, ScoresContainer scoresContainer) {
                if (pollingItem != null) {
                    mPolledBefore = pollingItem.getPolledBefore();
                    mTitleTextView.setText(pollingItem.getTitle());
                    mDescriptionTextView.setText(pollingItem.getDescription());
                    mOptions.clear();
                    mOptions.addAll(pollingItem.getOptions());
                    mOptionsType.setType(pollingItem.getOptionType());
                    mOptionsListAdapter.notifyDataSetChanged();
                    mFooterRelativeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();
    }

    private void poll() {
        if (mSelectedOptions.size() > 0) {
            WebApiHelper.poll(mPollingId, mSelectedOptions, TAG_REQUEST_POLL, new WebApiRequest.WebApiListener<PoleResult>() {
                @Override
                public void onResponse(PoleResult response, final ScoresContainer scoresContainer) {

                    new KhandevanehDialog(PollingActivity.this, "مرسی رفیق", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (scoresContainer != null) {
                                updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), new OnShakingFinishedListener() {
                                    @Override
                                    public void onShakingFinish() {
                                        finish();
                                    }
                                });
                            }
                        }
                    }).show();
                }

                @Override
                public void onErrorResponse(String errorMessage) {
                    new KhandevanehDialog(PollingActivity.this, errorMessage, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
                }
            }, null).send();
        } else {
            new KhandevanehDialog(this, "یه چیزی انتخاب کن دیگه!", null).show();
        }

    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityPolling_xeiButton_poll:
                if (mPolledBefore == PollingItem.POLLED_BEFORE_YES) {
                    new KhandevanehDialog(this, "یه بار بیشتر نمی‌تونی رای بدی رفیق!", null).show();
                } else if (mPolledBefore == PollingItem.POLLED_BEFORE_NO){
                    poll();
                }

                break;

            case R.id.activityPolling_xeiButton_showStatistics:
                if (mPolledBefore == PollingItem.POLLED_BEFORE_YES) {
                    PollingStatisticsActivity.start(this, mPollingId, mTitleTextView.getText().toString());
                } else if (mPolledBefore == PollingItem.POLLED_BEFORE_NO){
                    new KhandevanehDialog(this, "تو که شرکت نکردی آمار به چه دردت میخوره آخه؟", null).show();
                }
                break;
        }
    }
}
