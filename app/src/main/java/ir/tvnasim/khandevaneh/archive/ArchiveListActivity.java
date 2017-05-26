package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class ArchiveListActivity extends BaseActivity {

    private static final String KEY_EXTRA_CATEGORY_ID = "KEY_EXTRA_CATEGORY_ID";

    private static final String TAG_REQUEST_GET_ARCHIVE_LIST = "requestTag_pollingListActivity_getArchiveList";

    private RecyclerView mListRecyclerView;
    private ArchiveListAdapter mListAdapter;

    private String mCategoryId;
    private ArrayList<ArchiveListItem> mList = new ArrayList<>();

    private boolean isLoading = false;
    private int pageNo;

    public static void start(Context starter, String categoryId) {
        Intent intent = new Intent(starter, ArchiveListActivity.class);
        intent.putExtra(KEY_EXTRA_CATEGORY_ID, categoryId);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_ARCHIVE_LIST);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_list);

        this.mCategoryId = getIntent().getStringExtra(KEY_EXTRA_CATEGORY_ID);

        findViews();
        initRecyclerView();

        fetchArchiveListFromApi(pageNo);

    }

    private void findViews() {
        mListRecyclerView = (RecyclerView) findViewById(R.id.activityArchiveList_recyclerView_list);
    }

    private void initRecyclerView() {
        mListRecyclerView.setHasFixedSize(true);
        mListAdapter = new ArchiveListAdapter(mList);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListRecyclerView.setAdapter(mListAdapter);

        mListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        fetchArchiveListFromApi(++pageNo);
                    }
                }
            }
        });
    }

    private void fetchArchiveListFromApi(int pageNo) {

        isLoading = true;

        WebApiHelper.getArchiveList(mCategoryId, pageNo, TAG_REQUEST_GET_ARCHIVE_LIST, new WebApiRequest.WebApiListener<ArrayList<ArchiveListItem>>() {
            @Override
            public void onResponse(ArrayList<ArchiveListItem> archiveListItems, ScoresContainer scoresContainer) {
                if (archiveListItems != null) {
                    mList.addAll(archiveListItems);
                    mListAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG_DEBUG, "archiveListItems is null!");
                }
                isLoading = false;
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getArchiveList request failed:" + errorMessage);
                isLoading = false;
            }
        }, null).send();

    }

}
