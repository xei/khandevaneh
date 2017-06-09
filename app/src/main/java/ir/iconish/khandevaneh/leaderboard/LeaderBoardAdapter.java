package ir.iconish.khandevaneh.leaderboard;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.HelperFunctions;
import ir.iconish.khandevaneh.helper.imageloading.FrescoHelper;

/**
 * Created by hamidreza on 4/15/17.
 */

class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {

    private ArrayList<LeaderViewModel> mLeaders;
    private int mUserRank;

    LeaderBoardAdapter(ArrayList<LeaderViewModel> leaders) {
        mLeaders = leaders;
    }

    void setUserRank(int userRank) {
        this.mUserRank = userRank;
    }

    @Override
    public LeaderBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leader_board, parent, false);
        return new LeaderBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderBoardViewHolder holder, int position) {
        LeaderViewModel leader = mLeaders.get(position);
        holder.userRank.setText(String.format(holder.rowView.getContext().getString(R.string.leaderBoard_row_rank), HelperFunctions.convertNumberStringToPersian(String.valueOf(position + 1))));
        holder.userName.setText(leader.getFirstName() + ' ' + leader.getLastName());
        FrescoHelper.setImageUrl(holder.userAvatar, leader.getAvatar());
        holder.experienceLevel.setText(HelperFunctions.convertNumberStringToPersian(leader.getExperience()));

        if (position == mUserRank - 1) {
            holder.rowView.getLayoutParams().height = HelperFunctions.dpToPx(holder.rowView.getContext(), 80);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.rowView.getLayoutParams();
            int sideMargin = HelperFunctions.dpToPx(holder.rowView.getContext(), 8);
            int topMargin = HelperFunctions.dpToPx(holder.rowView.getContext(), 10);
            lp.setMargins(sideMargin, topMargin, sideMargin, 0);
            GradientDrawable background = (GradientDrawable) holder.rowView.getBackground();
            background.setCornerRadius(HelperFunctions.dpToPx(holder.rowView.getContext(), 80));
            holder.userAvatar.getLayoutParams().width = HelperFunctions.dpToPx(holder.rowView.getContext(), 80);
            holder.userAvatar.getLayoutParams().height = HelperFunctions.dpToPx(holder.rowView.getContext(), 80);
        } else {
            holder.rowView.getLayoutParams().height = HelperFunctions.dpToPx(holder.rowView.getContext(), 48);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.rowView.getLayoutParams();
            int sideMargin = HelperFunctions.dpToPx(holder.rowView.getContext(), 24);
            int topMargin = HelperFunctions.dpToPx(holder.rowView.getContext(), 10);
            lp.setMargins(sideMargin, topMargin, sideMargin, 0);
            GradientDrawable background = (GradientDrawable) holder.rowView.getBackground();
            background.setCornerRadius(HelperFunctions.dpToPx(holder.rowView.getContext(), 48));
            holder.userAvatar.getLayoutParams().width = HelperFunctions.dpToPx(holder.rowView.getContext(), 48);
            holder.userAvatar.getLayoutParams().height = HelperFunctions.dpToPx(holder.rowView.getContext(), 48);
        }
    }

    @Override
    public int getItemCount() {
        return mLeaders.size();
    }

    class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rowView;
        private TextView userRank;
        private TextView userName;
        private SimpleDraweeView userAvatar;
        private TextView experienceLevel;

        LeaderBoardViewHolder(View itemView) {
            super(itemView);

            rowView = (LinearLayout) itemView.findViewById(R.id.rowLeaderBoard_linearLayout_itemView);
            userName = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userName);
            userRank = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userRank);
            userAvatar = (SimpleDraweeView) itemView.findViewById(R.id.rowLeaderBoard_simpleDraweeView_userAvatar);
            experienceLevel = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userExperience);
        }
    }

}
