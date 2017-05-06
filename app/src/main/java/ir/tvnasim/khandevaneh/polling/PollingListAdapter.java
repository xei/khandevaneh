package ir.tvnasim.khandevaneh.polling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/1/17.
 * All rights reserved by Digikala.
 */

public class PollingListAdapter extends RecyclerView.Adapter<PollingListAdapter.ListItemViewHolder> {

    private ArrayList<PollingListItem> mList;

    public PollingListAdapter(ArrayList<PollingListItem> list) {
        this.mList = list;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        final PollingListItem item = mList.get(position);
        holder.title.setText(item.getTitle());
        try {
            FrescoHelper.setImageUrl(holder.image, item.getImageUrl());
        }catch (Exception e) {}


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                PollingActivity.start(clickedView.getContext(), item.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
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
