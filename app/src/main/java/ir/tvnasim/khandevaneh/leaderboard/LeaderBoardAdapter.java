package ir.tvnasim.khandevaneh.leaderboard;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;

/**
 * Created by hamidreza on 4/15/17.
 */

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {

    private ArrayList<LeaderViewModel> mLeaders;

    public LeaderBoardAdapter(ArrayList<LeaderViewModel> leaders) {
        mLeaders = leaders;
    }

    @Override
    public LeaderBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leader_board, parent, false);
        return new LeaderBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderBoardViewHolder holder, int position) {
        setBackgroundColor(holder.rowView.getContext(), holder.rowView, position);
        LeaderViewModel leader = mLeaders.get(position);
        holder.userName.setText(leader.getName());
        FrescoHelper.setImageUrl(holder.userAvatar, leader.getAvatar());
        holder.experienceLevel.setText(HelperFunctions.convertNumberStringToPersian(leader.getExperience()));
    }

    @Override
    public int getItemCount() {
        return mLeaders.size();
    }

    private void setBackgroundColor(Context context, View rowView, int position) {
        GradientDrawable backgroundDrawable = (GradientDrawable) rowView.getBackground();
        if (position % 2 == 0) {
            backgroundDrawable.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            backgroundDrawable.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rowView;
        private TextView userName;
        private SimpleDraweeView userAvatar;
        private TextView experienceLevel;

        LeaderBoardViewHolder(View itemView) {
            super(itemView);

            rowView = (LinearLayout) itemView;
            userName = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userName);
            userAvatar = (SimpleDraweeView) itemView.findViewById(R.id.rowLeaderBoard_simpleDraweeView_userAvatar);
            experienceLevel = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userExperience);
        }
    }

}
