package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.webapi.MockApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;

public class PollingListActivity extends BaseActivity {

    private RecyclerView mListRecyclerView;
    private PollingListAdapter mListAdapter;

    private ArrayList<PollingListItem> mList = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, PollingListActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_list);

        findViews();
        initRecyclerView();
//        setOnClickListeners();

        fetchPollingListFromApi();
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_polling;
    }

    private void findViews() {
        mListRecyclerView = (RecyclerView) findViewById(R.id.activityPolling_recyclerView_list);
    }

    private void initRecyclerView() {
        mListRecyclerView.setHasFixedSize(true);
        mListAdapter = new PollingListAdapter(mList);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListRecyclerView.setAdapter(mListAdapter);
    }


    private void fetchPollingListFromApi() {

        WebApiHelper.getPollingList("", 1, 2, "requestTag_pollingListActivity_getPollingList", new WebApiRequest.WebApiListener<ArrayList<PollingListItem>>() {
            @Override
            public void onResponse(ArrayList<PollingListItem> pollingListItems) {
                mList.clear();
                mList.addAll(pollingListItems);
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();

    }

}
