package ir.tvnasim.khandevaneh.polling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/1/17.
 * All rights reserved by Digikala.
 */

public class PollingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PollingListItem> mList;

    public PollingListAdapter(ArrayList<PollingListItem> list) {
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AdsBannerViewHolder) {
            AdsBannerViewHolder adsBannerViewHolder = (AdsBannerViewHolder) holder;
            // TODO ...
        } else if (holder instanceof ListItemViewHolder) {
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            final PollingListItem item = mList.get(position);
            listItemViewHolder.title.setText(item.getTitle());
            FrescoHelper.setImageUrl(listItemViewHolder.image, item.getImageUrl());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View clickedView) {
                    PollingActivity.start(clickedView.getContext(), item.getId());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class AdsBannerViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView bannerView;

        AdsBannerViewHolder(final View itemView) {
            super(itemView);

            bannerView = new SimpleDraweeView(itemView.getContext());
        }
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

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
