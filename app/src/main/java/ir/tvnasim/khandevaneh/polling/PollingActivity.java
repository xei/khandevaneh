package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.home.HomeMenuAdapter;
import ir.tvnasim.khandevaneh.home.HomeMenuLayoutManager;
import ir.tvnasim.khandevaneh.view.bannerslider.BannerFragment;

public class PollingActivity extends BaseActivity {

    private RecyclerView mListRecyclerView;
    private PollingListAdapter mListAdapter;

    private ArrayList<PollingListItem> mList = new ArrayList<>();

    public static void start(Context starter) {
        Intent intent = new Intent(starter, PollingActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        findViews();
        initRecyclerView();
//        setOnClickListeners();

        fetchBannersFromApi();
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


    private void fetchBannersFromApi() {

        //TODO: fetch from API
        PollingListItem item = new PollingListItem();
        item.setImageUrl(User.getInstance().getAvatar());
        item.setTitle("خنداننده برتر");
        for (int i = 0 ; i < 5 ; i++) {
            mList.add(item);
        }
        mListAdapter.notifyDataSetChanged();
    }

}
