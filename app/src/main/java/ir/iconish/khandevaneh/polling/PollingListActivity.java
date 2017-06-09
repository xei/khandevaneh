package ir.iconish.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.app.Banner;
import ir.iconish.khandevaneh.app.BaseActivity;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.app.ScoresContainer;

public class PollingListActivity extends BaseActivity {

    private static final String KEY_EXTRA_TYPE = "KEY_EXTRA_TYPE";

    private static final String TAG_REQUEST_GET_POLLING_LIST = "requestTag_pollingListActivity_getPollingList";
    private static final String TAG_REQUEST_GET_ADS_BANNER = "requestTag_pollingListActivity_getAdsBanner";

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
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_POLLING_LIST);
        tags.add(TAG_REQUEST_GET_ADS_BANNER);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_list);

        this.mType = getIntent().getIntExtra(KEY_EXTRA_TYPE, 0);

        findViews();
        initRecyclerView();

        fetchPollingListFromApi();
        fetchAdsBannerFromApi();
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

    // TODO: this is fucking
    private Banner findAppropriateBanner(ArrayList<Banner> banners) {
        if (banners != null) {
            if (mType == PollingListActivity.TYPE_POLLING) {
                for (Banner banner : banners) {
                    if(banner.getLocation().equals(Banner.LOCATION_POLLING_LIST)) {
                        return banner;
                    }
                }
            } else if (mType == PollingListActivity.TYPE_COMPETITION) {
                for (Banner banner : banners) {
                    if(banner.getLocation().equals(Banner.LOCATION_COMPETITION_LIST)) {
                        return banner;
                    }
                }
            }
        }
        return banners.get(0);
//        return null;
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

    private void fetchAdsBannerFromApi() {
        WebApiHelper.getBanners(TAG_REQUEST_GET_ADS_BANNER, new WebApiRequest.WebApiListener<ArrayList<Banner>>() {
            @Override
            public void onResponse(ArrayList<Banner> banners, ScoresContainer scoresContainer) {
                mListAdapter.setAdsBanner(findAppropriateBanner(banners));
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getPollingList request failed:" + errorMessage);
            }
        }, null).send();
    }

}
