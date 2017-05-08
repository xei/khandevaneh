package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.account.login.LoginActivity;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.leaderboard.LeaderBoardActivity;
import ir.tvnasim.khandevaneh.livelike.LiveLikeActivity;
import ir.tvnasim.khandevaneh.polling.PollingListActivity;
import ir.tvnasim.khandevaneh.store.StoreActivity;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.bannerslider.SliderView;

/**
 * Created by hamidreza on 4/20/17.
 */

public class HomeMenuAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private static final String TAG_DEBUG = HomeMenuAdapter.class.getSimpleName();

    static final int TYPE_VIEW_SLIDER = 0;
    static final int TYPE_VIEW_MENU_ITEM = 1;

    private ArrayList<HomeMenuItem> menuItems = new ArrayList<>();
    private ArrayList<Bundle> mBanners = new ArrayList<>();

    public HomeMenuAdapter() {

        // Competition
        HomeMenuItem competition = new HomeMenuItem();
        competition.setId(HomeMenuItem.ID_COMPETITION);
        competition.setBackgroundImageResourceId(R.drawable.ic_menu_home_competition);
        menuItems.add(competition);

        // Live Like
        HomeMenuItem liveLive = new HomeMenuItem();
        liveLive.setId(HomeMenuItem.ID_LIVE_LIKE);
        liveLive.setBackgroundImageResourceId(R.drawable.ic_menu_home_livelike);
        menuItems.add(liveLive);

        // Polling
        HomeMenuItem  polling = new HomeMenuItem();
        polling.setId(HomeMenuItem.ID_POLLING);
        polling.setBackgroundImageResourceId(R.drawable.ic_menu_home_polling);
        menuItems.add(polling);

        // Store
        HomeMenuItem  store = new HomeMenuItem();
        store.setId(HomeMenuItem.ID_STORE);
        store.setBackgroundImageResourceId(R.drawable.ic_menu_home_store);
        menuItems.add(store);

        // Leader Board
        HomeMenuItem  leaderboard = new HomeMenuItem();
        leaderboard.setId(HomeMenuItem.ID_LEADER_BOARD);
        leaderboard.setBackgroundImageResourceId(R.drawable.ic_menu_home_leaderboard);
        menuItems.add(leaderboard);

        // Campaign
        HomeMenuItem campaign = new HomeMenuItem();
        campaign.setId(HomeMenuItem.ID_CAMPAIGN);
        campaign.setBackgroundImageResourceId(R.drawable.ic_menu_home_campaign);
        menuItems.add(campaign);

        //Archive
        HomeMenuItem archive = new HomeMenuItem();
        archive.setId(HomeMenuItem.ID_ARCHIVE);
//        archive.setBackgroundImageResourceId(R.drawable.ic_menu_home_archive);
        archive.setBackgroundImageResourceId(R.drawable.ic_menu_home_archive);
        menuItems.add(archive);

        // Awards
        HomeMenuItem awards = new HomeMenuItem();
        awards.setId(HomeMenuItem.ID_AWARDS);
        awards.setBackgroundImageResourceId(R.drawable.ic_menu_home_awards);
        menuItems.add(awards);

    }

    public void setSliderBanners(ArrayList<Bundle> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_VIEW_SLIDER:
                return new HomeSliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_home_slider, parent, false));

            case TYPE_VIEW_MENU_ITEM:
                return new HomeMenuItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_home_item, parent, false));

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HomeSliderViewHolder) {
            HomeSliderViewHolder homeSliderViewHolder = (HomeSliderViewHolder) holder;
            homeSliderViewHolder.sliderView.setBanners(mBanners);

        } else if (holder instanceof HomeMenuItemViewHolder) {
            final HomeMenuItemViewHolder homeMenuItemViewHolder = (HomeMenuItemViewHolder) holder;

            HomeMenuItem item = menuItems.get(position - 1);
            homeMenuItemViewHolder.backgroundImage.setImageResource(item.getBackgroundImageResourceId());
            homeMenuItemViewHolder.itemView.setTag(item.getId());
            homeMenuItemViewHolder.itemView.setOnClickListener(this);
        } else {
            // trap
        }

    }

    @Override
    public int getItemCount() {
        return menuItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_VIEW_SLIDER;
        } else {
            return TYPE_VIEW_MENU_ITEM;
        }
    }

    @Override
    public void onClick(final View clickedView) {

        User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
            @Override
            public void isLoggedIn(boolean isLoggedIn) {
                Context context = clickedView.getContext();
                if (isLoggedIn) {
                    String id = (String) clickedView.getTag();
                    switch (id) {
                        case HomeMenuItem.ID_LIVE_LIKE:
                            LiveLikeActivity.start(context);
                            break;
                        case HomeMenuItem.ID_LEADER_BOARD:
                            LeaderBoardActivity.start(context);
                            break;
                        case HomeMenuItem.ID_STORE:
                            StoreActivity.start(context);
                            break;
                        case HomeMenuItem.ID_ARCHIVE:
                            new KhandevanehDialog(context, context.getString(R.string.inform_notImplemented), null).show();
//                ArchiveActivity.start(context);
                            break;
                        case HomeMenuItem.ID_POLLING:
                            PollingListActivity.start(context);
                            break;
                        case HomeMenuItem.ID_COMPETITION:
                            PollingListActivity.start(context);
                            break;
                        case HomeMenuItem.ID_CAMPAIGN:
                            new KhandevanehDialog(context, context.getString(R.string.inform_notImplemented), null).show();
                        break;
                        case HomeMenuItem.ID_AWARDS:
                            new KhandevanehDialog(context, context.getString(R.string.inform_notImplemented), null).show();
                            break;
                        default:
                            LogHelper.logError(TAG_DEBUG, "invalid item id!");
                    }
                } else {
                    new KhandevanehDialog(context, context.getString(R.string.inform_needLogin), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LoginActivity.start(clickedView.getContext());
                        }
                    }).show();
                }
            }
        });

    }

    private class HomeSliderViewHolder extends RecyclerView.ViewHolder {

        SliderView sliderView;
        PageIndicatorView pagerIndicator;

        HomeSliderViewHolder(final View itemView) {
            super(itemView);

            sliderView = (SliderView) itemView.findViewById(R.id.rowMenuHomeSlider_sliderView_slider);
            pagerIndicator = (PageIndicatorView) itemView.findViewById(R.id.rowMenuHomeSlider_pageIndicatorView_indicator);

            pagerIndicator.setDynamicCount(true);
            pagerIndicator.setAnimationType(AnimationType.SWAP);

            itemView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                    lp.height = itemView.getWidth() / 2;
                    itemView.setLayoutParams(lp);
                }
            });
        }
    }

    private class HomeMenuItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView backgroundImage;

        HomeMenuItemViewHolder(final View itemView) {
            super(itemView);

            this.itemView = itemView;
            backgroundImage = (ImageView) itemView.findViewById(R.id.rowMenuHome_imageView_image);

            itemView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                    lp.height = itemView.getWidth();
                    itemView.setLayoutParams(lp);
                }
            });

        }
    }

}
