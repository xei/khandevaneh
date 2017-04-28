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

public class StoreActivity extends BaseActivity {

    private static final int COUNT_SPAN_RECYCLER_VIEW = 2;

    private TextView mMelonTextView;
    private RecyclerView mItemsRecyclerView;
    private StoreAdapter mStoreAdapter;

    private ArrayList<ItemModel> mItems = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, StoreActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        findViews();
        initRecyclerView();
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_store;
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
        //TODO: API call
        ArrayList<ItemModel> leaders = new ArrayList<>();
        ItemModel leaderViewModel = new ItemModel();
        leaderViewModel.setTitle(HelperFunctions.persianizeDigitsInString("۴ بسته ۱۰۰ تایی"));
        leaderViewModel.setImage("http://img.bisms.ir//2015/06/rambod-javan-2.jpg");

        for (int i = 0 ; i < 50 ; i++) {
            leaders.add(leaderViewModel);
        }
        bindData(leaders);

    }

    private void bindData(ArrayList<ItemModel> items) {
        mItems.clear();
        mItems.addAll(items);
        mStoreAdapter.notifyDataSetChanged();
    }

}
