package ir.tvnasim.khandevaneh.store;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;

/**
 * Created by hamidreza on 4/19/17.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ItemViewHolder> {

    private static final String TAG_DEBUG = StoreAdapter.class.getSimpleName();

    private BaseActivity mActivity;

    private ArrayList<StoreItem> mItems;

    StoreAdapter(BaseActivity activity, ArrayList<StoreItem> items) {
        mActivity = activity;
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store, parent,  false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final StoreItem item = mItems.get(position);
        FrescoHelper.setImageUrl(holder.image, item.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getPrice() != null) {
                    new KhandevanehDialog(mActivity, "قیمت این بسته " + HelperFunctions.persianizeDigitsInString(item.getPrice()) + " تومنه ها", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            buyItem(item.getId(), mActivity);
                        }
                    }).show();
                } else {
                    new KhandevanehDialog(mActivity, mActivity.getString(R.string.inform_notImplemented), null).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void buyItem(final String itemId, final Context context) {
        WebApiHelper.buyItem(itemId, StoreActivity.TAG_REQUEST_BUY_ITEM, new WebApiRequest.WebApiListener<Object>() {
            @Override
            public void onResponse(Object response, final ScoresContainer scoresContainer) {
                new BuyConfirmationDialog(context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String confirmationCode = (String) v.getTag();
                        WebApiHelper.confirmBuy(itemId, confirmationCode, StoreActivity.TAG_REQUEST_CONFIRM_BUY, new WebApiRequest.WebApiListener<Object>() {
                            @Override
                            public void onResponse(Object response, final ScoresContainer scoresContainer) {
                                new KhandevanehDialog(context, "مرسی که خریدی :)", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (scoresContainer != null) {
                                            mActivity.updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                                        }
                                    }
                                }).show();
                            }

                            @Override
                            public void onErrorResponse(String errorMessage) {
                                LogHelper.logError(TAG_DEBUG, "confirmBuy request failed: " + errorMessage);
                                new KhandevanehDialog(context, errorMessage, null).show();
                            }
                        }, null).send();
                    }
                }).show();
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "buyItem request failed: " + errorMessage);
                new KhandevanehDialog(context, errorMessage, null).show();
            }
        }, null).send();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        SimpleDraweeView image;

        ItemViewHolder(final View itemView) {
            super(itemView);

            this.itemView = itemView;
            image = (SimpleDraweeView) itemView.findViewById(R.id.rowStore_simpleDraweeView_image);

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
