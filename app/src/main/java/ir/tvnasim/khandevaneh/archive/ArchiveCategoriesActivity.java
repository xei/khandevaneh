package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;

public class ArchiveCategoriesActivity extends BaseActivity {

    private static final String TAG_REQUEST_GET_ARCHIVE_CATEGORIES = "requestTag_pollingListActivity_getArchiveCategories";

    private RecyclerView mCategoriesRecyclerView;
    private ArchiveCategoriesAdapter mCategoriesAdapter;

    private ArrayList<ArchiveCategory> mCategories = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, ArchiveCategoriesActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_ARCHIVE_CATEGORIES);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_categories);

        findViews();
        initRecyclerView();

        fetchCategoriesFromApi();

    }

    private void findViews() {
        mCategoriesRecyclerView = (RecyclerView) findViewById(R.id.activityArchiveCategories_recyclerView_categories);
    }

    private void initRecyclerView() {
        mCategoriesRecyclerView.setHasFixedSize(true);
        mCategoriesAdapter = new ArchiveCategoriesAdapter(mCategories);
        mCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoriesRecyclerView.setAdapter(mCategoriesAdapter);
    }

    private void fetchCategoriesFromApi() {

        WebApiHelper.getArchiveCategories(TAG_REQUEST_GET_ARCHIVE_CATEGORIES, new WebApiRequest.WebApiListener<ArrayList<ArchiveCategory>>() {
            @Override
            public void onResponse(ArrayList<ArchiveCategory> archiveCategories, ScoresContainer scoresContainer) {
                if (archiveCategories != null) {
                    mCategories.clear();
                    mCategories.addAll(archiveCategories);
                    mCategoriesAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG_DEBUG, "archiveCategories is null!");
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getArchiveCategories request failed:" + errorMessage);
            }
        }, null).send();

    }

}
