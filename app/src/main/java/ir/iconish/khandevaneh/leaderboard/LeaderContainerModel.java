package ir.iconish.khandevaneh.leaderboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hamidreza on 5/4/17.
 */

public class LeaderContainerModel {

    @SerializedName("leader")
    private ArrayList<LeaderViewModel> leaders;

    @SerializedName("userRank")
    private int userRank;


    public ArrayList<LeaderViewModel> getLeaders() {
        return leaders;
    }

    public void setLeaders(ArrayList<LeaderViewModel> leaders) {
        this.leaders = leaders;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }
}
