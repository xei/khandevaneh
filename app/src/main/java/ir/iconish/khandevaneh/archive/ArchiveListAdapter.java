package ir.iconish.khandevaneh.archive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.account.User;
import ir.iconish.khandevaneh.app.Banner;
import ir.iconish.khandevaneh.helper.HelperFunctions;
import ir.iconish.khandevaneh.helper.imageloading.FrescoHelper;
import ir.iconish.khandevaneh.polling.PollingActivity;
import ir.iconish.khandevaneh.polling.PollingListItem;
import ir.iconish.khandevaneh.store.StoreActivity;
import ir.iconish.khandevaneh.view.KhandevanehDialog;
import ir.iconish.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/1/17.
 * All rights reserved by Digikala.
 */

class ArchiveListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_VIEW_ADS_BANNER = 1;
    private static final int TYPE_ITEM_VIEW_LIST_ITEM = 2;

    private Banner mAdsBanner;
    private ArrayList<ArchiveListItem> mList;

    ArchiveListAdapter(ArrayList<ArchiveListItem> list) {
        this.mList = list;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AdsBannerViewHolder) {
            AdsBannerViewHolder adsBannerViewHolder = (AdsBannerViewHolder) holder;
            FrescoHelper.setImageUrl(adsBannerViewHolder.bannerView, mAdsBanner.getImageUrl());
        } else if (holder instanceof ListItemViewHolder) {
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            final ArchiveListItem item;
//            if (mAdsBanner != null) {
//                item = mList.get(position - 1);
//            } else {
                item = mList.get(position);
//            }

            listItemViewHolder.title.setText(item.getTitle());
            FrescoHelper.setImageUrl(listItemViewHolder.image, item.getThumbnail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View clickedView) {
                    if (item.isAccessible()) {
                        // Archive item is free or bought.
                        ArchiveActivity.start(clickedView.getContext(), item.getId());
                    } else {
                        // Archive item is not accessible or not bought.
                        if (item.getPayableMelon() > 0) {
                            // The user must buy archive item.
                            if (item.getPayableMelon() <= User.getInstance().getMelonScore()) {
                                // The user has required credit to buy the item
                                new KhandevanehDialog(((ListItemViewHolder) holder).itemView.getContext(), "برای دسترسی به این آیتم باید " + HelperFunctions.convertNumberStringToPersian(String.valueOf(item.getPayableMelon())) + " تا هندونه خرج کنی.", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // The required credit will pay automatically.
                                        ArchiveActivity.start(clickedView.getContext(), item.getId());
                                    }
                                }).show();
                            } else {
                                // The user has not required credit to buy this item.
                                // So, refer him/her to store screen.
                                new KhandevanehDialog(((ListItemViewHolder) holder).itemView.getContext(), "برای دسترسی به این آیتم باید " + HelperFunctions.convertNumberStringToPersian(String.valueOf(item.getPayableMelon())) + " تا هندونه خرج کنی، که نداری!" + "\n" + "یه سر به فروشگاه بزن و اگه خواستی هندونه بخر.", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StoreActivity.start(((ListItemViewHolder) holder).itemView.getContext());
                                    }
                                }).show();
                            }
                        } else {
                            // Archive item is not accessible!
                            new KhandevanehDialog(((ListItemViewHolder) holder).itemView.getContext(), "این مورد در دسترس نیست!", null).show();
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        if (mAdsBanner != null) {
//            return mList.size() + 1;
//        } else {
            return mList.size();
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
