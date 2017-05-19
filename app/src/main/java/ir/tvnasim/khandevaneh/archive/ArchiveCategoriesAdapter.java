package ir.tvnasim.khandevaneh.archive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.Banner;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.polling.PollingActivity;
import ir.tvnasim.khandevaneh.polling.PollingListItem;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/1/17.
 * All rights reserved by Digikala.
 */

class ArchiveCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_VIEW_ADS_BANNER = 1;
    private static final int TYPE_ITEM_VIEW_LIST_ITEM = 2;

    private Banner mAdsBanner;
    private ArrayList<ArchiveCategory> mCategories;

    ArchiveCategoriesAdapter(ArrayList<ArchiveCategory> categories) {
        this.mCategories = categories;
    }

    public void setAdsBanner(Banner adsBanner) {
        this.mAdsBanner = adsBanner;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
//        if (mAdsBanner != null && position == 0) {
//            return TYPE_ITEM_VIEW_ADS_BANNER;
//        }
        return TYPE_ITEM_VIEW_LIST_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_VIEW_ADS_BANNER) {
            return new AdsBannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_list_banner, parent, false));
        } else if (viewType == TYPE_ITEM_VIEW_LIST_ITEM) {
            return new ListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_list, parent, false));
        } else {
            // Trap
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AdsBannerViewHolder) {
            AdsBannerViewHolder adsBannerViewHolder = (AdsBannerViewHolder) holder;
            FrescoHelper.setImageUrl(adsBannerViewHolder.bannerView, mAdsBanner.getImageUrl());
        } else if (holder instanceof ListItemViewHolder) {
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            final ArchiveCategory item;
//            if (mAdsBanner != null) {
//                item = mCategories.get(position - 1);
//            } else {
                item = mCategories.get(position);
//            }

            listItemViewHolder.title.setText(item.getTitle());
            FrescoHelper.setImageUrl(listItemViewHolder.image, item.getThumbnail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View clickedView) {
                    ArchiveListActivity.start(clickedView.getContext(), item.getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        if (mAdsBanner != null) {
//            return mCategories.size() + 1;
//        } else {
            return mCategories.size();
//        }
    }

    private class AdsBannerViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView bannerView;

        AdsBannerViewHolder(final View itemView) {
            super(itemView);

            bannerView = (SimpleDraweeView) itemView;
        }
    }

    private class ListItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        XeiTextView title;
        SimpleDraweeView image;

        ListItemViewHolder(final View itemView) {
            super(itemView);

            this.itemView = itemView;
            title = (XeiTextView) itemView.findViewById(R.id.rowPollingList_xeiTextView_title);
            image = (SimpleDraweeView) itemView.findViewById(R.id.rowPollingList_simpleDraweeView_image);

        }
    }
}
