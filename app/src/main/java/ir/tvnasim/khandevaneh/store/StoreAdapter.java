package ir.tvnasim.khandevaneh.store;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 4/19/17.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ItemViewHolder> {

    private ArrayList<ItemModel> mItems;

    public StoreAdapter(ArrayList<ItemModel> items) {
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store, parent,  false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemModel item = mItems.get(position);
        holder.title.setText(item.getTitle());
        FrescoHelper.setImageUrl(holder.image, item.getImage());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView image;
        XeiTextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);

            image = (SimpleDraweeView) itemView.findViewById(R.id.rowStore_simpleDraweeView_image);
            title = (XeiTextView) itemView.findViewById(R.id.rowStore_xeiTextView_title);
        }

    }

}
