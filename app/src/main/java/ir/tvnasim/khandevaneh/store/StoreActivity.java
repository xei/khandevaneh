package ir.tvnasim.khandevaneh.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.webapi.VolleyHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.model.store.StoreItem;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;

public class StoreActivity extends BaseActivity {

    private static final String TAG_REQUEST_GET_STORE_LIST = "requestTag_storeActivity_getStoreList";
    private static final int COUNT_SPAN_RECYCLER_VIEW = 2;

    private TextView mMelonTextView;
    private RecyclerView mItemsRecyclerView;
    private StoreAdapter mStoreAdapter;

    private ArrayList<StoreItem> mItems = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, StoreActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_STORE_LIST);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        findViews();
        initRecyclerView();
    }

    private void findViews() {
        mMelonTextView = (TextView) findViewById(R.id.activityStore_textView_melon);
        mItemsRecyclerView = (RecyclerView) findViewById(R.id.activityStore_recyclerView_items);
    }

    private void initRecyclerView() {
        mItemsRecyclerView.setHasFixedSize(true);
        mItemsRecyclerView.setLayoutManager(new GridLayoutManager(this, COUNT_SPAN_RECYCLER_VIEW));
        mStoreAdapter = new StoreAdapter(mItems);
        mItemsRecyclerView.setAdapter(mStoreAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        renderUserData();
        fetchDataFromApi();
    }

    private void renderUserData() {
        mMelonTextView.setText(String.format(getString(R.string.melon_count),
                HelperFunctions.convertNumberStringToPersian(String.valueOf(User.getInstance().getMelonScore()))));
    }

    private void fetchDataFromApi() {
        WebApiHelper.getStoreList(TAG_REQUEST_GET_STORE_LIST, new WebApiRequest.WebApiListener<ArrayList<StoreItem>>() {
            @Override
            public void onResponse(ArrayList<StoreItem> storeItems) {
                if (storeItems != null) {
                    bindData(storeItems);
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                new KhandevanehDialog(StoreActivity.this, "فروشگاه در دسترس نیست", null).show();
            }
        }, null).send();


    }

    private void bindData(ArrayList<StoreItem> items) {
        mItems.clear();
        mItems.addAll(items);
        mStoreAdapter.notifyDataSetChanged();
    }

}
