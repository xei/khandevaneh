package ir.iconish.khandevaneh.account.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.app.Banner;
import ir.iconish.khandevaneh.archive.ArchiveActivity;
import ir.iconish.khandevaneh.archive.ArchiveListItem;
import ir.iconish.khandevaneh.helper.imageloading.FrescoHelper;
import ir.iconish.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/1/17.
 * All rights reserved by Digikala.
 */

class AvatarsAdapter extends RecyclerView.Adapter<AvatarsAdapter.AvatarViewHolder> {

    private int[] resIds = {
            android.R.drawable.ic_input_add,
            R.drawable.avatar_bored,
            R.drawable.avatar_happy,
            R.drawable.avatar_happy_two,
            R.drawable.avatar_happy_three,
            R.drawable.avatar_loved,
            R.drawable.avatar_nerd,
            R.drawable.avatar_poker,
            R.drawable.avatar_smile,
    };

    private OnAvatarSelected mOnAvatarSelected;


    AvatarsAdapter(OnAvatarSelected onAvatarSelected) {
        this.mOnAvatarSelected = onAvatarSelected;
    }

    @Override
    public AvatarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_avatars, parent, false);
        return new AvatarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AvatarViewHolder holder, int position) {

        holder.avatarImageView.setImageResource(resIds[position]);
        holder.avatarImageView.setTag(resIds[position]);
        holder.avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAvatarSelected.onSelected(resIds[holder.getAdapterPosition()]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resIds.length;
    }

    class AvatarViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;

        AvatarViewHolder(final View itemView) {
            super(itemView);

            avatarImageView = (ImageView) itemView;
        }
    }

    public interface OnAvatarSelected {
        void onSelected(int resId);
    }

}
