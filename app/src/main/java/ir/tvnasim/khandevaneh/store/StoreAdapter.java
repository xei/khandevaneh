package ir.tvnasim.khandevaneh.store;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.helper.webapi.model.store.StoreItem;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 4/19/17.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ItemViewHolder> {

    private ArrayList<StoreItem> mItems;

    public StoreAdapter(ArrayList<StoreItem> items) {
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store, parent,  false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final StoreItem item = mItems.get(position);
        holder.title.setText(item.getTitle());
        FrescoHelper.setImageUrl(holder.image, item.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KhandevanehDialog(view.getContext(), "قیمت این بسته " + HelperFunctions.convertNumberStringToPersian(item.getPrice()) + " تومنه", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        SimpleDraweeView image;
        XeiTextView title;

        ItemViewHolder(final View itemView) {
            super(itemView);

            this.itemView = itemView;
            image = (SimpleDraweeView) itemView.findViewById(R.id.rowStore_simpleDraweeView_image);
            title = (XeiTextView) itemView.findViewById(R.id.rowStore_xeiTextView_title);

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
