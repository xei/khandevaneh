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

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;

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
        holder.userAvatar.setImageResource(R.drawable.logo); // TODO: st url
        holder.experienceLevel.setText(leader.getExperience());
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

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rowView;
        private TextView userName;
        private ImageView userAvatar;
        private TextView experienceLevel;

        public LeaderBoardViewHolder(View itemView) {
            super(itemView);

            rowView = (LinearLayout) itemView;
            userName = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userName);
            userAvatar = (ImageView) itemView.findViewById(R.id.rowLeaderBoard_imageView_userAvatar);
            experienceLevel = (TextView) itemView.findViewById(R.id.rowLeaderBoard_textView_userExperience);
        }
    }

}
