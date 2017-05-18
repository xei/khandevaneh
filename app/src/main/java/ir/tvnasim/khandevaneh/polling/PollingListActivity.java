package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.app.ScoresContainer;

public class PollingListActivity extends BaseActivity {

    private static final String KEY_EXTRA_TYPE = "KEY_EXTRA_TYPE";
    private static final String TAG_REQUEST_GET_POLLING_LIST = "requestTag_pollingListActivity_getPollingList";

    public static final int TYPE_POLLING = 1;
    public static final int TYPE_COMPETITION = 2;

    private RecyclerView mListRecyclerView;
    private PollingListAdapter mListAdapter;

    private int mType;
    private ArrayList<PollingListItem> mList = new ArrayList<>();

    public static void start(Context starter, int type) {
        Intent intent = new Intent(starter, PollingListActivity.class);
        intent.putExtra(KEY_EXTRA_TYPE, type);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_list);

        this.mType = getIntent().getIntExtra(KEY_EXTRA_TYPE, 0);

        findViews();
        initRecyclerView();

        fetchPollingListFromApi();
    }

    private void findViews() {
        mListRecyclerView = (RecyclerView) findViewById(R.id.activityPolling_recyclerView_list);
    }

    private void initRecyclerView() {
        mListRecyclerView.setHasFixedSize(true);
        mListAdapter = new PollingListAdapter(mType, mList);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListRecyclerView.setAdapter(mListAdapter);
    }


    private void fetchPollingListFromApi() {

        WebApiHelper.getPollingList(mType, TAG_REQUEST_GET_POLLING_LIST, new WebApiRequest.WebApiListener<ArrayList<PollingListItem>>() {
            @Override
            public void onResponse(ArrayList<PollingListItem> pollingListItems, ScoresContainer scoresContainer) {
                if (pollingListItems != null) {
                    mList.clear();
                    mList.addAll(pollingListItems);
                    mListAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG_DEBUG, "pollingListItems is null!");
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getPollingList request failed:" + errorMessage);
            }
        }, null).send();

    }

}
